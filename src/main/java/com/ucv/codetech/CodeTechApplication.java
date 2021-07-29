package com.ucv.codetech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeTechApplication.class, args);
    }
}
