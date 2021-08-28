package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The register API")
public interface RegisterApi {

    @ApiOperation(value = "Register student", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new student",
                    response = StudentFullCourseDisplayDto.class),
    })
    Long registerStudent(@Valid @RequestBody StudentDto studentDto);

    @ApiOperation(value = "Register instructor", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new instructor",
                    response = StudentFullCourseDisplayDto.class),
    })
    Long registerInstructor(@Valid @RequestBody InstructorDto instructorDto);
}
