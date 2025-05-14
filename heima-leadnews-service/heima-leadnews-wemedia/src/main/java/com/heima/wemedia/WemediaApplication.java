package com.heima.wemedia;

import com.heima.apis.article.IArticleClient;
import com.heima.apis.schedule.IScheduleClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync // 开启异步注解
@SpringBootApplication(scanBasePackages = "com.heima")
@EnableDiscoveryClient
@EnableScheduling // 开启定时任务支持
@MapperScan("com.heima.wemedia.mapper")
@EnableFeignClients(
        basePackages = "com.heima.apis",
        clients = {IArticleClient.class, IScheduleClient.class}
)
public class WemediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }

}