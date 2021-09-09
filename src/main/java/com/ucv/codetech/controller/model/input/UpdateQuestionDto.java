package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UpdateQuestionDto {

    @NotEmpty(message = "You should provide the question")
    @ApiModelProperty(required = true, value = "The new question", example = "Can final fields be changed?")
    private String question;

    @JsonProperty
    @NotNull(message = "You should specify if the question has multiple answers or not")
    @ApiModelProperty(value = "Marks if the question contains multiple answers")
    private boolean multipleAnswers;
}
