package com.ucv.codetech.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.jwt")
@Setter
@Getter
public class JwtConfiguration {
    private Long accessTokenTime;
    private Long refreshTokenTime;
    private String secret;
}
