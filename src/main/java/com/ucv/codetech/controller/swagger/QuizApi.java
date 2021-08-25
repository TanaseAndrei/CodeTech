package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The quiz API")
public interface QuizApi {

    Long addQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionDto questionDto);

    DisplayQuizDto getQuiz(@PathVariable("id") Long id);

    void deleteQuiz(@PathVariable("id") Long id);

    Long completeQuiz(@PathVariable("id") Long id);
}
