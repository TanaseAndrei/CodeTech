package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

@Api(value = "The question API")
public interface QuestionApi {

    @ApiOperation(value = "Add a new answer to a question", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added an answer to a quiz", response = Long.class),
            @ApiResponse(code = 404, message = "The question does not exist"),
            @ApiResponse(code = 403, message = "The user must be logged in")
    })
    Long addAnswer(@Schema(description = "The id of the question", example = "1") Long id, AnswerDto answerDto);

    @ApiOperation(value = "Updates a question", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated a question"),
            @ApiResponse(code = 404, message = "The question does not exist"),
            @ApiResponse(code = 401, message = "The instructor must be logged in")
    })
    void updateQuestion(Long id, UpdateQuestionDto updateQuestionDto);

    @ApiOperation(value = "Deletes a question", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted a question"),
            @ApiResponse(code = 404, message = "The question does not exist"),
            @ApiResponse(code = 401, message = "The instructor must be logged in")
    })
    void deleteQuestion(Long id);
}
