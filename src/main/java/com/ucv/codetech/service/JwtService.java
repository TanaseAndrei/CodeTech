package com.ucv.codetech.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

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

    private List<String> getAuthorities(User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
