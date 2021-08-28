package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ApiModel(value = "The comment dto used to create a comment")
public class UpdateCommentDto {

    @NotEmpty(message = "You should provide an updated comment")
    @ApiModelProperty(value = "The comment itself", example = "\"A very nice course!\"")
    private String description;
}
