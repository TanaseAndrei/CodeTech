package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ApiModel(description = "The question dto used to create a question")
public class QuestionDto {

    @NotBlank(message = "The question should not be empty")
    @ApiModelProperty(required = true, value = "The question", example = "Does Java use Bytecode?")
    private String question;

    @NotNull(message = "You should specify if there are multiple answers or not")
    @ApiModelProperty(required = true, value = "Specifies if there are multiple answers", example = "true")
    private boolean multipleAnswers;

    @NotEmpty(message = "You should provide some answers to this question")
    @ApiModelProperty(required = true, value = "The answers of the question")
    private List<AnswerDto> answers;
}
