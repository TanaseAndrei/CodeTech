package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Relation(collectionRelation = "lectures")
@EqualsAndHashCode(callSuper = true)
public class StudentFullLectureWrapperDisplayDto extends RepresentationModel<StudentFullLectureWrapperDisplayDto> {

    private Long id;

    @JsonIgnore
    private Long lectureId;

    private String name;

    private boolean completedLecture;

    @JsonIgnore
    private String lectureVideoName;

    private String description;

    @JsonIgnore
    private List<String> lectureFileNames = new ArrayList<>();
}
