package com.ucv.codetech.controller.exception.converter;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.exception.dto.AppExceptionDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@NoArgsConstructor
@Component
public class AppExceptionConverter {

    public AppExceptionDto exceptionToDto(AppException appException) {
        AppExceptionDto appExceptionDto = new AppExceptionDto();
        appExceptionDto.setHttpCode(appException.getHttpStatus().value());
        appExceptionDto.setMessage(appException.getMessage());
        appExceptionDto.setThrownTime(LocalDateTime.now());
        return appExceptionDto;
    }
}
