package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "This answer dto used to create an answer")
public class AnswerDto {

    @NotBlank(message = "You should specify the possible answer")
    @ApiModelProperty(required = true, value = "The answer", example = "Yes")
    private String answer;

    @JsonProperty
    @NotNull(message = "You should specify if the answer is correct or not")
    @ApiModelProperty(required = true, value = "Correct or not", example = "true")
    private boolean isCorrectAnswer;
}
