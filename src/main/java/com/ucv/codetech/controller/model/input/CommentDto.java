package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ApiModel(description = "The comment dto used to add a new comment")
public class CommentDto {

    @NotEmpty(message = "The comment should not be empty")
    @ApiModelProperty(required = true, value = "The comment itself", example = "\"It's a very nice course\"")
    private String description;
}
