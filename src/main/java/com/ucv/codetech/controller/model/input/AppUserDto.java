package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppUserDto {

    private String username;

    private String password;

    private String email;
}
