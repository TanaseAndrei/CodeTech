package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.output.InstructorFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewQuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

import java.util.List;

@Api(value = "The instructor API")
public interface InstructorApi {

    @ApiOperation(value = "Get the courses of the current instructor under a preview format", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE, response = InstructorPreviewCourseDisplayDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved instructor's courses"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    List<InstructorPreviewCourseDisplayDto> getCourses();

    @ApiOperation(value = "Get quizzes of an instructor", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = InstructorPreviewQuizDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved instructor's quizzes"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    List<InstructorPreviewQuizDto> getQuiz();

    @ApiOperation(value = "Upload files to a lecture", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            response = InstructorFullCourseDisplayDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved instructor's course"),
            @ApiResponse(code = 404, message = "The instructor's course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    InstructorFullCourseDisplayDto getCourse(@Schema(description = "The id of the course", example = "1") Long courseId);
}
