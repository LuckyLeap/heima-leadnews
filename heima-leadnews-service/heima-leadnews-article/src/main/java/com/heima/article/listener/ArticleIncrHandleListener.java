package com.heima.article.listener;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.heima.article.service.ApArticleService;
import com.heima.model.common.constants.HotArticleConstants;
import com.heima.model.mess.ArticleVisitStreamMess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArticleIncrHandleListener {

    private ApArticleService apArticleService;
    @Autowired
    public void setApArticleService(ApArticleService apArticleService) {
        this.apArticleService = apArticleService;
    }

    @RabbitListener(queues = HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_QUEUE)
    public void onMessage(String mess){
        if(StringUtils.isNotBlank(mess)){
            ArticleVisitStreamMess articleVisitStreamMess = JSON.parseObject(mess, ArticleVisitStreamMess.class);
            apArticleService.updateScore(articleVisitStreamMess);
            log.info("更新文章分值，articleId = {}", articleVisitStreamMess.getArticleId());
        }
    }
}