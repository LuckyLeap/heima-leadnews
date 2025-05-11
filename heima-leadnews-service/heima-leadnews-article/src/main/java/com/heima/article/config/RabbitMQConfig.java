package com.heima.article.config;

import com.heima.model.common.constants.WmNewsMessageConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 使用常量类中的队列名
    @Bean
    public Queue articleUpOrDownQueue() {
        return new Queue(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_QUEUE);
    }

    // 消息转换器
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}