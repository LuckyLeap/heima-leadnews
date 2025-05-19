package com.heima.article.cache;

import com.heima.article.service.HotArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class HotArticleCache {

    private final HotArticleService hotArticleService;
    @Autowired
    public HotArticleCache(HotArticleService hotArticleService) {
        this.hotArticleService = hotArticleService;
    }

    // 应用启动后立即执行一次，之后每天2:00执行一次
    @PostConstruct // 应用启动后执行一次
    @Scheduled(cron = "0 0 2 * * ?")
    public void handle() {
        if (log.isDebugEnabled()) {
            log.debug("热点文章分值计算调度任务开始执行...");
        }
        try {
            hotArticleService.computeHotArticle();
        } catch (Exception e) {
            log.error("热点文章计算异常 | msg: {} | trace: ", e.getMessage(), e);
        }
    }
}