package org.ict;

import org.ict.content.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@EnableEurekaClient
@SpringBootApplication
public class uuidApp {
    public static void main(String[] args) {
        //for test
        String t = "abc";
        byte[]b = Info.string2Bytes(t);
        try {
            System.out.println(Info.bytes2String(b));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //---
        SpringApplication.run(uuidApp.class, args);
    }
}
