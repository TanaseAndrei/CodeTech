package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.CourseLectureDto;
import com.ucv.codetech.model.Lecture;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CourseLectureConverter {

    public Lecture courseLectureDtoToCourseLecture(CourseLectureDto courseLectureDto) {
        Lecture lecture = new Lecture();
        lecture.setName(courseLectureDto.getName());
        lecture.setDescription(courseLectureDto.getDescription());
        lecture.setLectureFileNames(Collections.emptyList());
        return lecture;
    }
}
