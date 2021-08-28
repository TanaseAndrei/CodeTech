package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "This represents the answer entity that will be displayed")
public class DisplayAnswerDto {

    @Schema(description = "The id of answer", example = "1")
    private Long id;

    @Schema(description = "The description of the answer", example = "Yes")
    private String description;
}
