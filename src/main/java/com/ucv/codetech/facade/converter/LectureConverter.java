package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
import com.ucv.codetech.model.Lecture;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class LectureConverter {

    public Lecture dtoToEntity(LectureDto lectureDto) {
        Lecture lecture = new Lecture();
        lecture.setName(lectureDto.getName());
        lecture.setDescription(lectureDto.getDescription());
        lecture.setLectureFileNames(Collections.emptyList());
        return lecture;
    }

    public DisplayLectureDto entityToDisplayLectureDto(Lecture lecture) {
        DisplayLectureDto displayLectureDto = new DisplayLectureDto();
        displayLectureDto.setId(lecture.getId());
        displayLectureDto.setLectureVideoName(lecture.getLectureVideoName());
        displayLectureDto.setDescription(lecture.getDescription());
        displayLectureDto.setLectureFileNames(lecture.getLectureFileNames());
        displayLectureDto.setName(lecture.getName());
        return displayLectureDto;
    }
}
