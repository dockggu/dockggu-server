package com.DXsprint.dockggu.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class StorageConfig {
    // S3를 등록한 사람이 전달받은 접속하기 위한 key 값
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    // S3를 등록한 사람이 전달받은 접속하기 위한 secret key 값
    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    // S3를 등록한 사람이 S3를 사용할 지역
    @Value("${cloud.aws.region.static}")
    private String region;
    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }
}
