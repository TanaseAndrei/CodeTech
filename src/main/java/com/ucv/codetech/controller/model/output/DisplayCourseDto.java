package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class DisplayCourseDto extends RepresentationModel<DisplayCourseDto>  {

    private Long id;

    private String name;

    private String instructorName;

    private int enrolledStudents;

    private int numberOfLectures;

    @JsonIgnore
    private String coverImageName;
}
