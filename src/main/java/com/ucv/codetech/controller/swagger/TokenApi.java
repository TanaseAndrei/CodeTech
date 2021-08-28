package com.ucv.codetech.controller.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "The token API")
public interface TokenApi {

    @ApiOperation(value = "Refreshes the authorization token that is inside the authorization header", httpMethod = "GET",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully refreshed the token", response = void.class),
            @ApiResponse(code = 401, message = "Needs to be logged in", response = void.class)
    })
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
