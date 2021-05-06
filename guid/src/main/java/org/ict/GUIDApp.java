package org.ict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Entry-Point Class
 * Run the main function
 *
 * @author TianzeWu
 * @date 2021-04-23
 */
@EnableEurekaClient
@SpringBootApplication
public class GUIDApp{
    public static void main(String[] args) {
        SpringApplication.run(GUIDApp.class, args);
    }

}
