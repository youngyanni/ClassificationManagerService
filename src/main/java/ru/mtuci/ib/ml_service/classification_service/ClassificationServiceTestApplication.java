package ru.mtuci.ib.ml_service.classification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ClassificationServiceTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassificationServiceTestApplication.class, args);
    }

}
