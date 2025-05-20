package com.heima.user;

import com.heima.apis.wemedia.IWemediaClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.heima")
@EnableDiscoveryClient // 启用注册中心
@MapperScan("com.heima.user.mapper")
@EnableFeignClients(basePackages = "com.heima.apis", clients = {IWemediaClient.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

}