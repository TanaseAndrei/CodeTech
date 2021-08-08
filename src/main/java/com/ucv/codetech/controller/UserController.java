package com.ucv.codetech.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.controller.swagger.UserApi;
import com.ucv.codetech.facade.UserFacade;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserFacade userFacade;

    @PostMapping(path = "/student/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerStudent(@RequestBody StudentDto studentDto) {
        return userFacade.registerStudent(studentDto);
    }

    @PostMapping(path = "/instructor/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerInstructor(@RequestBody InstructorDto instructorDto) {
        return userFacade.registerInstructor(instructorDto);
    }

    @GetMapping(path = "/refresh-token")
    @ResponseStatus(HttpStatus.CREATED)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser appUser = userFacade.getAppUser(username);
                Role role = appUser.getRole();
                String accessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", Stream.of(role).map(Role::name).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> headerTokens = new HashMap<>();
                headerTokens.put("access_token", accessToken);
                headerTokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), headerTokens); //tokens will be in a nice json format
            } catch (Exception exception) {
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error); //tokens will be in a nice json format
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
