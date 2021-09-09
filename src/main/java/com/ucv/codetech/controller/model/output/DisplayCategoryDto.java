package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "Dto used to display a category")
public class DisplayCategoryDto {

    @ApiModelProperty(value = "The id of the category", example = "1")
    private Long id;

    @ApiModelProperty(value = "The name of the category", example = "C#")
    private String name;
}
