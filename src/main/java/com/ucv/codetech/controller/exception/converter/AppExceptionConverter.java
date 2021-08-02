package com.ucv.codetech.controller.exception.converter;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.exception.dto.AppExceptionDto;
import com.ucv.codetech.controller.exception.dto.ValidationExceptionDto;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public ValidationExceptionDto validationExceptionToDto(MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationExceptionDto validationExceptionDto = new ValidationExceptionDto();
        validationExceptionDto.setHttpCode(HttpStatus.BAD_REQUEST.value());
        validationExceptionDto.setMessage("Some fields are not valid");
        Map<String, String> fieldsWithErrors = new LinkedHashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError)objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            fieldsWithErrors.put(fieldName, errorMessage);
        });
        validationExceptionDto.setFieldsWithErrors(fieldsWithErrors);
        validationExceptionDto.setThrownTime(LocalDateTime.now());
        return validationExceptionDto;
    }
}
