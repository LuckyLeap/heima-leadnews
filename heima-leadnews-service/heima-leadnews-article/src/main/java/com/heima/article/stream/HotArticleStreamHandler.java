package com.heima.article.stream;

import com.alibaba.fastjson.JSON;
import com.heima.model.common.constants.HotArticleConstants;
import com.heima.model.mess.ArticleVisitStreamMess;
import com.heima.model.mess.UpdateArticleMess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class HotArticleStreamHandler {

    private final RabbitTemplate rabbitTemplate;
    private final Map<Long, ArticleStats> articleStatsMap = new ConcurrentHashMap<>();

    public HotArticleStreamHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = HotArticleConstants.HOT_ARTICLE_SCORE_QUEUE)
    public void handleMessage(String message) {
        UpdateArticleMess mess = JSON.parseObject(message, UpdateArticleMess.class);
        articleStatsMap.compute(mess.getArticleId(), (id, stats) -> {
            if (stats == null) {
                stats = new ArticleStats();
            }
            switch (mess.getType()) {
                case COLLECTION:
                    stats.collect += mess.getAdd();
                    break;
                case COMMENT:
                    stats.comment += mess.getAdd();
                    break;
                case LIKES:
                    stats.like += mess.getAdd();
                    break;
                case VIEWS:
                    stats.view += mess.getAdd();
                    break;
            }
            return stats;
        });
    }

    @Scheduled(fixedRate = 15000) // 每15秒执行一次
    public void processTimeWindow() {
        articleStatsMap.forEach((articleId, stats) -> {
            ArticleVisitStreamMess mess = new ArticleVisitStreamMess();
            mess.setArticleId(articleId);
            mess.setCollect(stats.collect);
            mess.setComment(stats.comment);
            mess.setLike(stats.like);
            mess.setView(stats.view);

            log.info("聚合消息处理之后的结果为:{}", JSON.toJSONString(mess));
            rabbitTemplate.convertAndSend(
                    HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_QUEUE,
                    JSON.toJSONString(mess)
            );
        });
        articleStatsMap.clear(); // 清空当前窗口数据
    }

    //  文章统计
    private static class ArticleStats {
        int collect;
        int comment;
        int like;
        int view;

        ArticleStats() {
            this.collect = 0;
            this.comment = 0;
            this.like = 0;
            this.view = 0;
        }
    }
}