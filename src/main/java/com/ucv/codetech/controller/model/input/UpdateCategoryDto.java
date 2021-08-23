package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UpdateCategoryDto {

    @NotEmpty(message = "You should provide the new name of the category")
    private String name;
}
