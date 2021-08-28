package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ApiModel(description = "This represents the question entity that will be displayed")
public class DisplayQuestionDto {

    @Schema(description = "The id of the question", example = "1")
    private Long id;

    @Schema(description = "The question", example = "Is Java using Bytecode?")
    private String question;

    @Schema(description = "Specifies if the question has multiple answers", example = "true")
    private boolean multipleAnswers;

    @Schema(description = "The answers of the question")
    private List<DisplayAnswerDto> answers = new ArrayList<>();
}
