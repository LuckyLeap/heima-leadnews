package com.heima.wemedia.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.utils.common.SensitiveWordUtil;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SensitiveWordCache {
    private static List<String> sensitiveWords = new ArrayList<>();

    private WmSensitiveMapper wmSensitiveMapper;
    @Autowired
    public void setWmSensitiveMapper(WmSensitiveMapper wmSensitiveMapper) {
        this.wmSensitiveMapper = wmSensitiveMapper;
    }

    // 应用启动后立即执行一次，之后每小时更新一次
    @PostConstruct // 应用启动后执行一次
    @Scheduled(fixedRate = 3600000) // 每隔1小时执行一次
    public void refreshSensitiveWords() {
        log.info("SensitiveWordCache - refreshSensitiveWords");
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(
                Wrappers.<WmSensitive>lambdaQuery()
                        .select(WmSensitive::getSensitives)
        );

        List<String> newWords = wmSensitives.stream()
                .map(WmSensitive::getSensitives)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());

        // 使用线程安全的更新方式
        synchronized (this) {
            sensitiveWords = newWords;
            SensitiveWordUtil.initMap(sensitiveWords); // 更新工具类词库
        }
    }

    // 获取敏感词列表
    public static List<String> getSensitiveWords() {
        return new ArrayList<>(sensitiveWords); // 返回副本保证线程安全
    }
}