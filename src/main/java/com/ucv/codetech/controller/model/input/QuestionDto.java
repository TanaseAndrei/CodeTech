package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ApiModel(description = "This represents the question dto used for input")
public class QuestionDto {

    @NotBlank(message = "The question should not be empty")
    @ApiModelProperty(required = true)
    @Schema(description = "The question", example = "Does Java use Bytecode?")
    private String question;

    @NotNull(message = "You should specify if there are multiple answers or not")
    @ApiModelProperty(required = true)
    @Schema(description = "Specifies if there are multiple answers", example = "true")
    private boolean multipleAnswers;

    @NotEmpty(message = "You should provide some answers to this question")
    @ApiModelProperty(required = true)
    @Schema(description = "The answers of the question")
    private List<AnswerDto> answers;
}
