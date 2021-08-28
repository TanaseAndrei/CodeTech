package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ApiModel(value = "This represents the base class of the user/instructor")
public class AppUserDto {

    @NotEmpty(message = "Username should not be empty")
    @ApiModelProperty(required = true)
    @Schema(description = "The name of the user", example = "user1234")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    @ApiModelProperty(required = true)
    @Schema(description = "The password of the user", example = "ko!e12@oo32cX!")
    private String password;

    @NotEmpty(message = "The email should not be blank")
    @ApiModelProperty(required = true)
    @Schema(description = "The email of the user", example = "a@yahoo.com")
    private String email;
}
