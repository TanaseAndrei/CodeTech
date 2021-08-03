package com.ucv.codetech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//TODO delete exclude option when done with testing the api
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAsync
public class CodeTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeTechApplication.class, args);
    }
}
