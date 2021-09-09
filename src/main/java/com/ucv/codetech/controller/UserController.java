package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.AppUserDto;
import com.ucv.codetech.controller.swagger.UserApi;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserFacade userFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerUser(@Valid @RequestBody AppUserDto appUserDto) {
        return userFacade.registerUser(appUserDto);
    }
}
