package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "The category dto used to create a category")
public class CategoryDto {

    @NotBlank(message = "The category name should not be empty")
    @ApiModelProperty(value = "The name of the category", example = "C#")
    private String name;
}
