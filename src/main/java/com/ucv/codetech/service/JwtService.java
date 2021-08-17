package com.ucv.codetech.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";

    public Map<String, String> createSecurityTokens(HttpServletRequest httpServletRequest, User user, Algorithm algorithm) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", createAccessToken(httpServletRequest, user.getUsername(), getAuthorities(user), algorithm));
        tokens.put("refresh_token", createRefreshToken(httpServletRequest, user.getUsername(), algorithm));
        return tokens;
    }

    public String createAccessToken(HttpServletRequest httpServletRequest, String username, List<String> authorities, Algorithm algorithm) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).withIssuer(httpServletRequest.getRequestURL().toString())
                .withClaim("roles", authorities)
                .sign(algorithm);
    }

    public String createRefreshToken(HttpServletRequest httpServletRequest, String username, Algorithm algorithm) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000)).withIssuer(httpServletRequest.getRequestURL().toString())
                .sign(algorithm);
    }

    public String getSubject(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeader(request);
        String refreshToken = authorizationHeader.substring(BEARER.length());
        Algorithm algorithm = getAlgorithm();
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
        return decodedJWT.getSubject();
    }

    public String getAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        checkAuthorizationHeader(header);
        return header;
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256("secret".getBytes());
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeader(request);
        return authorizationHeader.substring(BEARER.length());
    }

    public void handleException(HttpServletResponse response, Exception exception) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error); //tokens will be in a nice json format
    }

    private void checkAuthorizationHeader(String header) {
        if (StringUtils.isEmpty(header) || !header.contains(BEARER)) {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private List<String> getAuthorities(User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
