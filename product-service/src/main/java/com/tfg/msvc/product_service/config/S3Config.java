package com.tfg.msvc.product_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
@Configuration
public class S3Config {
    @Value("${aws.acces.key}")
    private String awsAccessKeyId;
    @Value("${aws.secret.key}")
    private String awsSecretAccessKey;
    @Value("${aws.region}")
    private String awsRegion;
    @Bean
    public S3AsyncClient getS3AsyncClient() {
        AwsCredentials basicCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        return S3AsyncClient.builder()
                .region(Region.of(this.awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                .endpointOverride(URI.create("https://s3.eu-north-1.amazonaws.com"))
                .build();
    }
    @Bean
    public S3Presigner getS3Presigner() {
        AwsCredentials basicCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        return S3Presigner.builder()
                .region(Region.of(this.awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                .build();
    }
}
