package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class DisplayLectureDto extends RepresentationModel<DisplayLectureDto> {

    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    private List<String> lectureFileNames;

    @JsonIgnore
    private String lectureVideoName;
}
