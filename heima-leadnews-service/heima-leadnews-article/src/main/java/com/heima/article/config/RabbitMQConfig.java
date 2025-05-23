package com.heima.article.config;

import com.heima.model.common.constants.WmNewsMessageConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 使用常量类中的队列名
    @Bean
    public Queue articleUpOrDownQueue() {
        return new Queue(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_QUEUE);
    }

}