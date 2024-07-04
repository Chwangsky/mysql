// package com.woo.boardback.etc;
// package com.woo.boardback.config;

// import org.hibernate.cache.spi.Region;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class AwsConfig {

// @Value("${aws.accessKeyId}")
// private String accessKeyId;

// @Value("${aws.secretAccessKey}")
// private String secretAccessKey;

// @Value("${aws.region}")
// private String region;

// @Bean
// public S3Client s3Client() {
// return S3Client.builder()
// .region(Region.of(region))
// .credentialsProvider(StaticCredentialsProvider.create(
// AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
// .build();
// }

// }
