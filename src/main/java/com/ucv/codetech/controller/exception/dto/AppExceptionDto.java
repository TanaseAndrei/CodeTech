package com.ucv.codetech.controller.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Setter
@Getter
public class AppExceptionDto {

    private int httpCode;
    private String message;
    private String thrownTime;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public void setThrownTime(LocalDateTime localDateTime) {
        this.thrownTime = localDateTime.format(dateTimeFormatter);
    }
}
