package com.heima.search.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticSearchConfig {

    private String hostname;
    private int port;

    @Bean
    public RestHighLevelClient client(){
        System.out.println(hostname);
        System.out.println(port);
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost(
                        hostname,
                        port,
                        "http"
                )
        ));
    }
}