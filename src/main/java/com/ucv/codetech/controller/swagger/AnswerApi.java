package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The answer API")
public interface AnswerApi {

    void update(Long id, @Valid @RequestBody UpdateAnswerDto updateAnswerDto);

    void delete(Long id);

    boolean isCorrect(Long id);
}
