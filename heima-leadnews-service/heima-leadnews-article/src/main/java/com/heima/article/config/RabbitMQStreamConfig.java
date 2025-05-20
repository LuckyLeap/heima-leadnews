package com.heima.article.config;

import com.heima.model.common.constants.HotArticleConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Setter
@Getter
@Configuration
@EnableRabbit
@ConfigurationProperties(prefix="rabbit")
public class RabbitMQStreamConfig {

    private String hosts; // RabbitMQ地址
    private String group; // 队列名前缀

    // 定义流处理相关组件
    private static final String STREAM_EXCHANGE = "stream_exchange";
    private static final String INPUT_QUEUE = "stream_input";
    private static final String OUTPUT_QUEUE = "stream_output";
    private static final String ROUTING_KEY = "stream_route";

    // 输入队列
    @Bean
    public Queue inputQueue() {
        return new Queue(group + "_" + INPUT_QUEUE, true);
    }

    // 输出队列
    @Bean
    public Queue outputQueue() {
        return new Queue(group + "_" + OUTPUT_QUEUE, true);
    }

    // 直连交换机（根据业务选择topic/fanout等类型）
    @Bean
    public DirectExchange streamExchange() {
        return new DirectExchange(STREAM_EXCHANGE);
    }

    // 绑定关系（实现消息路由）
    @Bean
    public Binding inputBinding() {
        return BindingBuilder.bind(inputQueue())
                .to(streamExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding outputBinding() {
        return BindingBuilder.bind(outputQueue())
                .to(streamExchange())
                .with(ROUTING_KEY);
    }

    // 消息模板配置
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        // 设置重试策略（对应原RETRIES_CONFIG）
        template.setRetryTemplate(new RetryTemplate());
        return template;
    }

    // 消息监听容器工厂（配置并发等参数）
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        // 配置并发参数（对应原CLIENT_ID_CONFIG等）
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    // 创建 HOT_ARTICLE_SCORE_QUEUE 队列
    @Bean
    public Queue hotArticleScoreQueue() {
        return new Queue(HotArticleConstants.HOT_ARTICLE_SCORE_QUEUE, true);
    }

    // 创建 HOT_ARTICLE_INCR_HANDLE_QUEUE 队列
    @Bean
    public Queue hotArticleIncrHandleQueue() {
        return new Queue(HotArticleConstants.HOT_ARTICLE_INCR_HANDLE_QUEUE, true);
    }

    // JSON序列化
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}