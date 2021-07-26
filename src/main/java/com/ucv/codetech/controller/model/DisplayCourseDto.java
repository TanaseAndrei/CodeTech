package com.ucv.codetech.controller.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DisplayCourseDto {

    private Long id;

    private String name;

    private String instructorName;

    private int enrolledStudents;

    private int numberOfLectures;

    private String base64ConvertedImage;
}
