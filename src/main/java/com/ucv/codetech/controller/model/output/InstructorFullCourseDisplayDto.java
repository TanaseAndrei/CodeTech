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
public class InstructorFullCourseDisplayDto extends RepresentationModel<InstructorFullCourseDisplayDto> {

    private Long courseId;

    private String description;

    private int enrolledStudents;

    private int numberOfLectures;

    private List<DisplayCommentDto> comments;

    private String coverImageName;

    private List<InstructorFullLectureDisplayDto> fullLectureDisplayDtos = new ArrayList<>();
}
