package com.ucv.codetech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucv.codetech.controller.swagger.TokenApi;
import com.ucv.codetech.facade.UserFacade;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController implements TokenApi {

    private final UserFacade userFacade;
    private final JwtService jwtService;

    @GetMapping(path = "/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = jwtService.getSubject(request);
            AppUser appUser = userFacade.getAppUser(username);
            Role role = appUser.getRole();
            Map<String, String> headerTokens = new HashMap<>();
            headerTokens.put("access_token", jwtService.createAccessToken(request, appUser.getUsername(), Collections.singletonList(role.name()), jwtService.getAlgorithm()));
            headerTokens.put("refresh_token", jwtService.getToken(request));
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), headerTokens); //tokens will be in a nice json format
        } catch (Exception exception) {
            response.setStatus(FORBIDDEN.value());
            jwtService.handleException(response, exception);
        }
    }
}
