package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto used to display course for a student, including checkboxes for completion. This will be used with enrolled course class
 */
@Setter
@Getter
public class StudentFullCourseDisplayDto {

    private Long id;

    private String name;

    private String enrolledDate;

    private int numberOfCompletedLectures;

    private int numberOfLectures;

    private boolean isCourseCompleted;

    private String coverImageName;

    List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos = new ArrayList<>();
}
