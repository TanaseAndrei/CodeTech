package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "This represents the base class of the user and instructor")
public class AppUserDto {

    @NotEmpty(message = "Username should not be empty")
    @ApiModelProperty(required = true, value = "The name of the user", example = "user1234")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @ApiModelProperty(required = true, value = "The password of the user", example = "ko!e12@oo32cX!")
    private String password;

    @NotEmpty(message = "The email should not be blank")
    @Email(message = "You should give a valid email address")
    @ApiModelProperty(required = true, value = "The email of the user", example = "a@yahoo.com")
    private String email;

    @NotNull(message = "The role of the user should be specified")
    @ApiModelProperty(required = true, value = "The role of the user", example = "STUDENT")
    private RoleDto roleDto;
}
