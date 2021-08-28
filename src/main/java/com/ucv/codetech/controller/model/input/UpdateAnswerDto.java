package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "The answer dto used to update an answer")
public class UpdateAnswerDto {

    @NotEmpty(message = "You should provide a description to the answer")
    @ApiModelProperty(required = true, value = "The new answer description", example = "Yes")
    private String description;

    @JsonProperty
    @NotNull(message = "You should specify if it's the correct answer or not")
    @ApiModelProperty(required = true, value = "The flag that specifies if the answer is the correct answer or not", allowableValues = "true")
    private boolean correctAnswer;
}
