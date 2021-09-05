package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.AppUserDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The users' API")
public interface RegisterApi {

    @ApiOperation(value = "Register user", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new student",
                    response = StudentFullCourseDisplayDto.class),
    })
    Long registerUser(@Valid @RequestBody AppUserDto appUserDto);
}
