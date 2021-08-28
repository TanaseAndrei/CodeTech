package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

@Api(value = "The answer API")
public interface AnswerApi {

    @ApiOperation(value = "Update an answer", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated an answer"),
            @ApiResponse(code = 404, message = "The answer does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void update(@Schema(description = "The id of the answer", example = "1") Long id, UpdateAnswerDto updateAnswerDto);

    @ApiOperation(value = "Delete an answer", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted an answer"),
            @ApiResponse(code = 404, message = "The answer does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void delete(@Schema(description = "The id of the answer", example = "1") Long id);

    @ApiOperation(value = "Delete an answer", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE, response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got an answer"),
            @ApiResponse(code = 404, message = "The answer does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    boolean isCorrect(@Schema(description = "The id of the answer", example = "1") Long id);
}
