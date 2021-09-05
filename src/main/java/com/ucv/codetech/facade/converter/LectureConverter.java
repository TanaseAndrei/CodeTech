package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
import com.ucv.codetech.controller.model.output.InstructorFullLectureDisplayDto;
import com.ucv.codetech.model.Lecture;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        displayLectureDto.setName(lecture.getName());
        return displayLectureDto;
    }

    public List<InstructorFullLectureDisplayDto> entitiesToInstructorFullLectureDisplayDtos(List<Lecture> lectures) {
        return lectures
                .stream()
                .map(this::entityToInstructorFullLectureDisplayDto)
                .collect(Collectors.toList());
    }

    public InstructorFullLectureDisplayDto entityToInstructorFullLectureDisplayDto(Lecture lecture) {
        InstructorFullLectureDisplayDto instructorFullLectureDisplayDto = new InstructorFullLectureDisplayDto();
        instructorFullLectureDisplayDto.setLectureId(lecture.getId());
        instructorFullLectureDisplayDto.setLectureFileNames(lecture.getLectureFileNames());
        instructorFullLectureDisplayDto.setDescription(lecture.getDescription());
        instructorFullLectureDisplayDto.setLectureVideoName(lecture.getLectureVideoName());
        return instructorFullLectureDisplayDto;
    }
}
