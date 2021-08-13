package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullLectureWrapperDisplayDto;
import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.model.LectureWrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrolledCourseConverter {

    public List<StudentCourseDisplayDto> entitiesToStudentCourseDisplayDtos(List<EnrolledCourse> enrolledCourses) {
        return enrolledCourses
                .stream()
                .map(this::entityToStudentCourseDisplayDto)
                .collect(Collectors.toList());
    }

    public StudentCourseDisplayDto entityToStudentCourseDisplayDto(EnrolledCourse enrolledCourse) {
        StudentCourseDisplayDto studentCourseDisplayDto = new StudentCourseDisplayDto();
        studentCourseDisplayDto.setEnrolledCourseId(enrolledCourse.getId());
        studentCourseDisplayDto.setCourseCompleted(enrolledCourse.isCourseCompleted());
        studentCourseDisplayDto.setName(enrolledCourse.getCourse().getName());
        studentCourseDisplayDto.setNumberOfCompletedLectures(enrolledCourse.getNumberOfCompletedLectures());
        studentCourseDisplayDto.setNumberOfLectures(enrolledCourse.getNumberOfLectures());
        studentCourseDisplayDto.setCoverImageName(enrolledCourse.getCourse().getCoverImageName());
        return studentCourseDisplayDto;
    }

    public StudentFullCourseDisplayDto entityToStudentFullCourseDisplayDto(EnrolledCourse enrolledCourse) {
        StudentFullCourseDisplayDto studentFullCourseDisplayDto = new StudentFullCourseDisplayDto();
        studentFullCourseDisplayDto.setCoverImageName(enrolledCourse.getCourse().getCoverImageName());
        studentFullCourseDisplayDto.setId(enrolledCourse.getId());
        studentFullCourseDisplayDto.setName(enrolledCourse.getCourse().getName());
        studentFullCourseDisplayDto.setEnrolledDate(enrolledCourse.getEnrolledDate());
        studentFullCourseDisplayDto.setNumberOfCompletedLectures(enrolledCourse.getNumberOfCompletedLectures());
        studentFullCourseDisplayDto.setNumberOfLectures(enrolledCourse.getNumberOfLectures());
        studentFullCourseDisplayDto.setCourseCompleted(enrolledCourse.isCourseCompleted());
        studentFullCourseDisplayDto.setLectureWrapperDisplayDtos(lectureWrappersToStudentFullLectureWrapperDisplayDtos(enrolledCourse.getLectureWrappers()));
        return studentFullCourseDisplayDto;
    }

    private List<StudentFullLectureWrapperDisplayDto> lectureWrappersToStudentFullLectureWrapperDisplayDtos(List<LectureWrapper> lectureWrappers) {
        return lectureWrappers
                .stream()
                .map(this::lectureWrapperToStudentFullLectureWrapperDisplayDto)
                .collect(Collectors.toList());
    }

    private StudentFullLectureWrapperDisplayDto lectureWrapperToStudentFullLectureWrapperDisplayDto(LectureWrapper lectureWrapper) {
        StudentFullLectureWrapperDisplayDto studentFullLectureWrapperDisplayDto = new StudentFullLectureWrapperDisplayDto();
        studentFullLectureWrapperDisplayDto.setId(lectureWrapper.getId());
        studentFullLectureWrapperDisplayDto.setName(lectureWrapper.getLecture().getName());
        studentFullLectureWrapperDisplayDto.setLectureId(lectureWrapper.getLecture().getId());
        studentFullLectureWrapperDisplayDto.setDescription(lectureWrapper.getLecture().getDescription());
        studentFullLectureWrapperDisplayDto.setLectureVideoName(lectureWrapper.getLecture().getLectureVideoName());
        studentFullLectureWrapperDisplayDto.setLectureFileNames(lectureWrapper.getLecture().getLectureFileNames());
        studentFullLectureWrapperDisplayDto.setCompletedLecture(lectureWrapper.isCompletedLecture());
        return studentFullLectureWrapperDisplayDto;
    }
}
