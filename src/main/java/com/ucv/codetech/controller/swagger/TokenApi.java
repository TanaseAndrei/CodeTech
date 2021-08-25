package com.ucv.codetech.controller.swagger;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "The token API")
public interface TokenApi {

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
