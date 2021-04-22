package org.ict;

import org.ict.content.BaseInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.UnsupportedEncodingException;

@EnableEurekaClient
@SpringBootApplication
public class UUIDApp {
    public static void main(String[] args) {
        SpringApplication.run(UUIDApp.class, args);
    }
}
