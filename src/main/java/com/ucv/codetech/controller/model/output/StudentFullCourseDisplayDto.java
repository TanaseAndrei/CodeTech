package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class StudentFullCourseDisplayDto extends RepresentationModel<StudentFullCourseDisplayDto> {

    private Long id;

    private String name;

    private String enrolledDate;

    private int numberOfCompletedLectures;

    private int numberOfLectures;

    private boolean isCourseCompleted;

    @JsonIgnore
    private String coverImageName;

    List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos = new ArrayList<>();
}
