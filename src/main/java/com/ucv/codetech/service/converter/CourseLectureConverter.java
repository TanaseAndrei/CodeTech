package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.input.CourseLectureDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
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

    public DisplayLectureDto lectureToDisplayLectureDto(Lecture lecture) {
        DisplayLectureDto displayLectureDto = new DisplayLectureDto();
        displayLectureDto.setLectureVideoName(lecture.getLectureVideoName());
        displayLectureDto.setDescription(lecture.getDescription());
        displayLectureDto.setLectureFileNames(lecture.getLectureFileNames());
        displayLectureDto.setName(lecture.getName());
        return displayLectureDto;
    }
}
