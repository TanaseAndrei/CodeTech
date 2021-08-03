package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class StudentDto extends AppUserDto {

    @NotNull(message = "The role should not be null")
    private RoleDto roleDto;
}
