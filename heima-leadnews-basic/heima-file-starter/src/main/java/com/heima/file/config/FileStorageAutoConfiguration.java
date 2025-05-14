package com.heima.file.config;

import com.heima.file.service.FileStorageService;
import com.heima.file.service.impl.MinIOFileStorageServiceImpl;
import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
public class FileStorageAutoConfiguration {

    @Bean
    public FileStorageService fileStorageService(
            MinioClient minioClient, // Spring 会自动注入 MinIOConfig 中定义的 MinioClient
            MinIOConfigProperties properties // 自动绑定配置属性
    ) {
        return new MinIOFileStorageServiceImpl(minioClient, properties);
    }

}