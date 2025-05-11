package com.heima.wemedia.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.heima.apis.article.IArticleClient;
import com.heima.common.tess4j.Tess4jClient;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.SensitiveWordUtil;
import com.heima.wemedia.cache.SensitiveWordCache;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    private final WmNewsMapper wmNewsMapper;
    private final WmChannelMapper wmChannelMapper;
    private final WmUserMapper wmUserMapper;
    @Autowired
    public WmNewsAutoScanServiceImpl(WmNewsMapper wmNewsMapper, WmChannelMapper wmChannelMapper, WmUserMapper wmUserMapper){
        this.wmNewsMapper = wmNewsMapper;
        this.wmChannelMapper = wmChannelMapper;
        this.wmUserMapper = wmUserMapper;
    }

    /**
     * 自媒体文章审核
     * @param id 自媒体文章id
     */
    @Override
    @Async //开启异步
    public void autoScanWmNews(Integer id) {
        //1.查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);
        if(wmNews == null){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章不存在");
        }

        if(wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            //从内容中提取纯文本内容和图片
            Map<String,Object> textAndImages = handleTextAndImages(wmNews);

            //自管理的敏感词过滤
            boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"), wmNews);
            if(!isSensitive) return;

            //2.审核文本内容
            //3.审核图片
            boolean isImageScan =  handleImageScan((List<String>) textAndImages.get("images"),wmNews);
            if(!isImageScan) return;

            //4.审核成功，保存app端的相关的文章数据
            ResponseResult<Object> responseResult = saveAppArticle(wmNews);
            if(!responseResult.getCode().equals(200)){
                throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
            }
            //回填article_id
            wmNews.setArticleId((Long) responseResult.getData());
            updateWmNews(wmNews,(short) 9,"审核成功");
        }
    }

    /**
     * 自管理的敏感词过滤
     * @param content 文章内容
     * @param wmNews 自媒体文章对象
     */
    private boolean handleSensitiveScan(String content, WmNews wmNews) {
        boolean flag = true;

        if (content == null || content.isEmpty()) {
            return true;
        }

        List<String> sensitiveList = SensitiveWordCache.getSensitiveWords();

        if (sensitiveList.isEmpty()) {
            return true;
        }

        // 无需初始化，直接使用已缓存的词库
        Map<String, Integer> map = SensitiveWordUtil.matchWords(wmNews.getTitle() + "-" + content);
        if (!map.isEmpty()) {
            updateWmNews(wmNews, (short) 2, "当前文章中存在违规内容" + map);
            flag = false;
        }

        return flag;
    }

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private Tess4jClient tess4jClient;

    /**
     * 审核图片
     * @param images 图片列表
     * @param wmNews 自媒体文章对象
     */
    private boolean handleImageScan(List<String> images, WmNews wmNews) {
        if (images == null || images.isEmpty()) {
            return true;
        }

        // 图片去重
        images = images.stream().distinct().collect(Collectors.toList());

        List<byte[]> imageList = new ArrayList<>();

        // 下载图片
        for (String image : images) {
            byte[] bytes = fileStorageService.downLoadFile(image);
            if (bytes == null || bytes.length == 0) {
                // 下载失败，视为审核失败或记录日志
                return false;
            }
            imageList.add(bytes);
        }

        // 审核图片
        for (byte[] bytes : imageList) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
                BufferedImage imageFile = ImageIO.read(in);
                if (imageFile == null) {
                    // 图像无法解析，视为不确定信息，需要人工审核
                    updateWmNews(wmNews, (short) 3, "当前文章中存在不确定内容");
                    return false;
                }

                // 调用tess4j进行图片识别
                String result = tess4jClient.doOCR(imageFile);
                boolean isSensitive = handleSensitiveScan(result, wmNews);
                if (!isSensitive) {
                    updateWmNews(wmNews, (short) 2, "当前文章中存在违规内容");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Autowired
    private IArticleClient articleClient;

    /**
     * 保存app端相关的文章数据
     * @param wmNews 自媒体文章
     */
    private ResponseResult<Object> saveAppArticle(WmNews wmNews) {

        ArticleDto dto = new ArticleDto();
        //属性的拷贝
        BeanUtils.copyProperties(wmNews,dto);
        //文章的布局
        dto.setLayout(wmNews.getType());
        //频道
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if(wmChannel != null){
            dto.setChannelName(wmChannel.getName());
        }

        //作者
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if(wmUser != null){
            dto.setAuthorName(wmUser.getName());
        }

        //设置文章id
        if(wmNews.getArticleId() != null){
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());

        return articleClient.saveArticle(dto);
    }

    /**
     * 修改文章内容
     * @param wmNews 文章对象
     */
    private void updateWmNews(WmNews wmNews, short status, String reason) {
        wmNews.setStatus(status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

    /**
     * 1。从自媒体文章的内容中提取文本和图片
     * 2.提取文章的封面图片
     * @param wmNews 文章对象
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {
        //存储纯文本内容
        StringBuilder stringBuilder = new StringBuilder();

        List<String> images = new ArrayList<>();

        //1。从自媒体文章的内容中提取文本和图片
        if(StringUtils.isNotBlank(wmNews.getContent())){
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }

                if (map.get("type").equals("image")){
                    images.add((String) map.get("value"));
                }
            }
        }
        //2.提取文章的封面图片
        if(StringUtils.isNotBlank(wmNews.getImages())){
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content",stringBuilder.toString());
        resultMap.put("images",images);
        return resultMap;
    }
}