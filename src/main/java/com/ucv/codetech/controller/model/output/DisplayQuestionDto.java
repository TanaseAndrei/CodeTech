package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class DisplayQuestionDto extends RepresentationModel<DisplayQuestionDto> {

    private Long id;

    private String question;

    private boolean multipleAnswers;

    private List<DisplayAnswerDto> answers;
}
