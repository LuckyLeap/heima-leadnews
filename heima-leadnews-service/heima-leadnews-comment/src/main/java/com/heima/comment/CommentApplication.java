package com.heima.comment;

import com.heima.apis.article.IArticleClient;
import com.heima.apis.user.IUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.heima")
@EnableDiscoveryClient//注册服务
@ServletComponentScan//启动Servlet组件扫描
@EnableFeignClients(basePackages = "com.heima.apis",
        clients = {IUserClient.class, IArticleClient.class})
public class CommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class,args);
    }

}