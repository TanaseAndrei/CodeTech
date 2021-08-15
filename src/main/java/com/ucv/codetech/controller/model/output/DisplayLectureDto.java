package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class DisplayLectureDto extends RepresentationModel<DisplayLectureDto> {

    private Long id;

    private String name;
}
