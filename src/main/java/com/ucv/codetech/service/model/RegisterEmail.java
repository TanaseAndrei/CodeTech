package com.ucv.codetech.service.model;

import com.ucv.codetech.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterEmail {
    private final String username;
    private final String to;
    private final Role role;
}
