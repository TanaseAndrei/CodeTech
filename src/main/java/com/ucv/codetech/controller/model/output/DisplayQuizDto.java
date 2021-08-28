package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel(description = "This represents the quiz entity that will be displayed")
public class DisplayQuizDto {

    private List<DisplayQuestionDto> questions;
}
