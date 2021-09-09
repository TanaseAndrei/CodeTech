package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.exception.dto.ValidationExceptionDto;
import com.ucv.codetech.controller.model.input.AppUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The users API")
public interface UserApi {

    @ApiOperation(value = "Register user", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new user",
                    response = Long.class),
            @ApiResponse(code = 400, message = "The fields does not meet the criteria",
                    response = ValidationExceptionDto.class),
    })
    Long registerUser(@Valid @RequestBody AppUserDto appUserDto);
}
