package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DisplayQuestionDto {

    private Long id;

    private String question;

    private boolean multipleAnswers;

    private List<DisplayAnswerDto> answers;
}
