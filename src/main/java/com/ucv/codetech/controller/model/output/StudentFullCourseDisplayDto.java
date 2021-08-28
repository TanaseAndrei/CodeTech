package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "This represents the full enrolled course of")
public class StudentFullCourseDisplayDto extends RepresentationModel<StudentFullCourseDisplayDto> {

    @Schema(description = "The id of the course", example = "1")
    private Long id;

    @Schema(description = "The name of the course", example = "Java 101")
    private String name;

    @Schema(description = "The date when the current user enrolled in", example = "2021-03-25 14:25:33")
    private String enrolledDate;

    @Schema(description = "The number of the completed lectures from the current course", example = "5")
    private int numberOfCompletedLectures;

    @Schema(description = "The number of the total lectures from the current course", example = "15")
    private int numberOfLectures;

    @Schema(description = "The status of the current enrolled course, if the student completed it or not", example = "true")
    private boolean isCourseCompleted;

    @Schema(description = "The name of the cover image associated with the course", example = "213vr321v-2r3vr123v123-v341.jpg")
    private String coverImageName;

    @Schema(description = "The comments from the current course")
    private List<DisplayCommentDto> displayCommentDtos = new ArrayList<>();

    @Schema(description = "The lectures from the enrolled course")
    private List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos = new ArrayList<>();
}
