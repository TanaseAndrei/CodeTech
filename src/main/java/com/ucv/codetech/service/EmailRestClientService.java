package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.service.configuration.EmailUrlConfiguration;
import com.ucv.codetech.service.model.RegisterEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class EmailRestClientService {

    private final RestTemplate restTemplate;
    private final EmailUrlConfiguration emailUrlConfiguration;

    @Async
    public void sendEmail(RegisterEmail registerEmail) {
        try {
            log.info("Sending email to {}", registerEmail.getTo());
            restTemplate.postForEntity(emailUrlConfiguration.getRegisterUrl(), new HttpEntity<>(registerEmail, createHttpHeaders()), void.class);
            log.info("Sent email to {}", registerEmail.getTo());
        } catch (RuntimeException runtimeException) {
            log.error("Error sending email to {}", registerEmail.getTo());
            throw new AppException("Error sending email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
