package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ApiModel(description = "The category dto used to update a category")
public class UpdateCategoryDto {

    @NotEmpty(message = "You should provide the new name of the category")
    @ApiModelProperty(value = "The new name of the category", example = "Rust")
    private String name;
}
