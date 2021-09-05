package com.ucv.codetech.service.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service.mail")
@Setter
public class EmailUrlConfiguration {
    private String baseUrl;
    private String registerUrl;

    public String getRegisterUrl() {
        return getBaseUrl() + registerUrl;
    }

    private String getBaseUrl() {
        return baseUrl;
    }
}
