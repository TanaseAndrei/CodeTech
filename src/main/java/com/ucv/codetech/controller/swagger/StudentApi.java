package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentPreviewCourseDisplayDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(value = "The student API")
public interface StudentApi {

    @ApiOperation(value = "Get courses of the current logged student", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = StudentPreviewCourseDisplayDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully return the previews of the enrolled courses of the current logged student",
                    response = StudentPreviewCourseDisplayDto.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    List<StudentPreviewCourseDisplayDto> getCourses();

    @ApiOperation(value = "Get certifications of the current logged student", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = StudentCertificationDisplayDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned the previews of the certifications of the current logged student",
                    response = StudentCertificationDisplayDto.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    List<StudentCertificationDisplayDto> getCertifications();

    @ApiOperation(value = "Completes the lecture from the enrolled course of the current logged student", httpMethod = "PATCH",
            produces = MediaType.APPLICATION_JSON_VALUE, response = StudentCertificationDisplayDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully completed the lecture"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void completeLecture(@PathVariable("id") @Schema(description = "The id of the lecture from the enrolled course", example = "512")
                         @NotEmpty(message = "The wrapped lecture id should be provided") Long id);

    @ApiOperation(value = "Get enrolled course of the current logged student", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = StudentFullCourseDisplayDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned the enrolled course of the student",
                    response = StudentFullCourseDisplayDto.class),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    StudentFullCourseDisplayDto getCourse(@PathVariable("id") @Schema(description = "The id of the lecture from the enrolled course", example = "512")
                                          @NotEmpty(message = "The wrapped lecture id should be provided") Long id);
}
