package com.ucv.codetech.controller.model.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class InstructorFullLectureDisplayDto extends RepresentationModel<InstructorFullLectureDisplayDto> {

    private Long lectureId;

    private String description;

    private List<String> lectureFileNames = new ArrayList<>();

    private String lectureVideoName;
}
