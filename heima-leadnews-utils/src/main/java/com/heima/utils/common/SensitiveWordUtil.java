package com.heima.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 敏感词过滤工具类（线程安全）
 * 1. 使用Trie树结构存储敏感词
 * 2. 支持多线程安全初始化与匹配
 * 3. 内置AC自动机匹配算法优化性能
 */
public class SensitiveWordUtil {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordUtil.class);
    private static volatile TrieNode root = new TrieNode();
    private static final Object initLock = new Object();
    private static boolean isInitialized = false;

    // Trie节点定义
    private static class TrieNode {
        Map<Character, TrieNode> children = new ConcurrentHashMap<>();
        boolean isEnd = false;
        TrieNode failNode; // AC自动机失败指针
    }

    /**
     * 初始化敏感词库（线程安全）
     * @param words 敏感词集合
     */
    public static void initMap(Collection<String> words) {
        if (words == null || words.isEmpty()) {
            logger.warn("敏感词集合为空，已清空现有词库");
            synchronized (initLock) {
                root = new TrieNode();
                isInitialized = false;
            }
            return;
        }

        synchronized (initLock) {
            try {
                TrieNode newRoot = buildTrie(words);
                buildFailureLinks(newRoot); // 构建AC自动机失败指针
                root = newRoot;
                isInitialized = true;
                logger.info("敏感词库初始化完成，词条数量：{}", words.size());
            } catch (Exception e) {
                logger.error("敏感词库初始化失败", e);
                throw new RuntimeException("敏感词库初始化失败", e);
            }
        }
    }

    // 构建Trie树
    private static TrieNode buildTrie(Collection<String> words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            if (word == null || word.trim().isEmpty()) continue;

            TrieNode current = root;
            for (char c : word.toCharArray()) {
                current.children.computeIfAbsent(c, k -> new TrieNode());
                current = current.children.get(c);
            }
            current.isEnd = true;
        }
        return root;
    }

    // 构建AC自动机失败指针
    private static void buildFailureLinks(TrieNode root) {
        Queue<TrieNode> queue = new LinkedList<>();
        root.failNode = null;
        queue.offer(root);

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();

            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();

                if (current == root) {
                    child.failNode = root;
                } else {
                    TrieNode failNode = current.failNode;
                    while (failNode != null) {
                        if (failNode.children.containsKey(c)) {
                            child.failNode = failNode.children.get(c);
                            break;
                        }
                        failNode = failNode.failNode;
                    }
                    if (failNode == null) {
                        child.failNode = root;
                    }
                }
                queue.offer(child);
            }
        }
    }

    /**
     * 检查文本中是否包含敏感词
     * @param text 待检查文本
     * @return 匹配到的敏感词及出现次数
     */
    public static Map<String, Integer> matchWords(String text) {
        if (!isInitialized) {
            logger.warn("敏感词库未初始化，跳过检查");
            return Collections.emptyMap();
        }
        if (text == null || text.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> result = new HashMap<>();
        TrieNode current = root;
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            // AC自动机跳转逻辑
            while (!current.children.containsKey(c) && current != root) {
                current = current.failNode;
            }

            current = current.children.getOrDefault(c, root);
            TrieNode temp = current;

            // 检查当前路径是否构成敏感词
            while (temp != root) {
                if (temp.isEnd) {
                    int wordLength = getWordLength(temp, chars, i);
                    String word = new String(chars, i - wordLength + 1, wordLength);
                    result.put(word, result.getOrDefault(word, 0) + 1);
                    i = i - wordLength + 1; // 跳过已匹配部分
                    break;
                }
                temp = temp.failNode;
            }
        }
        return result;
    }

    // 获取敏感词长度（回溯到词起点）
    private static int getWordLength(TrieNode node, char[] chars, int endIndex) {
        int length = 0;
        while (node != root) {
            length++;
            node = node.failNode;
        }
        return length;
    }

    /**
     * 快速检查是否包含敏感词（性能优化）
     * @param text 待检查文本
     * @return 是否包含敏感词
     */
    public static boolean containsSensitiveWord(String text) {
        if (!isInitialized || text == null || text.isEmpty()) {
            return false;
        }

        TrieNode current = root;
        char[] chars = text.toCharArray();

        for (char c : chars) {
            while (!current.children.containsKey(c) && current != root) {
                current = current.failNode;
            }

            current = current.children.getOrDefault(c, root);
            if (current.isEnd) {
                return true;
            }
        }
        return false;
    }
}