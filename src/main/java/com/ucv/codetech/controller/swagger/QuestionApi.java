package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The question API")
public interface QuestionApi {

    Long addAnswer(@PathVariable("id") Long id, @Valid @RequestBody AnswerDto answerDto);

    void updateQuestion(@PathVariable("id") Long id, @Valid @RequestBody UpdateQuestionDto updateQuestionDto);

    void deleteQuestion(@PathVariable("id") Long id);
}
