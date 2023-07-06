package ru.cft.shift.crowdfundingplatformapi.configuration;

import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@Data
@ConfigurationProperties("minio")
public class MinioConfiguration {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;

    @PostConstruct
    private void init() {
        log.info("Конфиг для S3 {}", this);
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .credentials(accessKey, secretKey)
                .endpoint(url)
                .build();
    }

}
