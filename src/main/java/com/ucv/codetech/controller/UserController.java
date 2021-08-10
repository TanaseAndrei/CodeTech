package com.ucv.codetech.controller;

import com.ucv.codetech.controller.swagger.UserApi;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserFacade userFacade;
}
