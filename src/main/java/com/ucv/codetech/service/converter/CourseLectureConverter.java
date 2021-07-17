package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.CourseLectureDto;
import com.ucv.codetech.model.CourseLecture;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CourseLectureConverter {

    public CourseLecture courseLectureDtoToCourseLecture(CourseLectureDto courseLectureDto) {
        CourseLecture courseLecture = new CourseLecture();
        courseLecture.setName(courseLectureDto.getName());
        courseLecture.setDescription(courseLectureDto.getDescription());
        courseLecture.setLectureFilePaths(Collections.emptyList());
        return courseLecture;
    }
}
