package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DisplayQuizDto {

    private List<DisplayQuestionDto> questions;
}
