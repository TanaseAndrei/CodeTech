package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StudentLectureWrapperDisplayDto {

    private Long id;

    private String name;

    private boolean completedLecture;

    private String lectureVideoName;

    private List<String> lectureFileNames = new ArrayList<>();
}
