package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

@Api(value = "The quiz API")
public interface QuizApi {

    @ApiOperation(value = "Add a question to a quiz", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added a question to a quiz", response = Long.class),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    Long addQuestion(@Schema(description = "The id of the quiz", example = "2") Long id, QuestionDto questionDto);

    @ApiOperation(value = "Get a quiz", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE, response = DisplayQuizDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully retrieved the quiz"),
            @ApiResponse(code = 403, message = "The user must be logged in")
    })
    DisplayQuizDto getQuiz(Long id);

    @ApiOperation(value = "Completes the lecture from the enrolled course of the current logged student", httpMethod = "PATCH",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully completed the lecture"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void deleteQuiz(Long id);

    @ApiOperation(value = "Completes the lecture from the enrolled course of the current logged student", httpMethod = "PATCH",
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully completed the lecture"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    Long completeQuiz(Long id);
}
