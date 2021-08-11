package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentLectureWrapperDisplayDto;
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
        studentCourseDisplayDto.setId(enrolledCourse.getId());
        studentCourseDisplayDto.setName(enrolledCourse.getCourse().getName());
        studentCourseDisplayDto.setEnrolledDate(enrolledCourse.getEnrolledDate());
        studentCourseDisplayDto.setNumberOfCompletedLectures(enrolledCourse.getNumberOfCompletedLectures());
        studentCourseDisplayDto.setNumberOfLectures(enrolledCourse.getNumberOfLectures());
        studentCourseDisplayDto.setCourseCompleted(enrolledCourse.isCourseCompleted());
        studentCourseDisplayDto.setLectureWrapperDisplayDtos(lectureWrappersToStudentLectureWrapperDisplayDtos(enrolledCourse.getLectureWrappers()));
        return studentCourseDisplayDto;
    }

    private List<StudentLectureWrapperDisplayDto> lectureWrappersToStudentLectureWrapperDisplayDtos(List<LectureWrapper> lectureWrappers) {
        return lectureWrappers
                .stream()
                .map(this::lectureWrapperToStudentLectureWrapperDisplayDto)
                .collect(Collectors.toList());
    }

    private StudentLectureWrapperDisplayDto lectureWrapperToStudentLectureWrapperDisplayDto(LectureWrapper lectureWrapper) {
        StudentLectureWrapperDisplayDto studentLectureWrapperDisplayDto = new StudentLectureWrapperDisplayDto();
        studentLectureWrapperDisplayDto.setId(lectureWrapper.getId());
        studentLectureWrapperDisplayDto.setName(lectureWrapper.getLecture().getName());
        studentLectureWrapperDisplayDto.setCompletedLecture(lectureWrapper.isCompletedLecture());
        studentLectureWrapperDisplayDto.setLectureVideoName(lectureWrapper.getLecture().getLectureVideoName());
        studentLectureWrapperDisplayDto.setLectureFileNames(lectureWrapper.getLecture().getLectureFileNames());
        return studentLectureWrapperDisplayDto;
    }
}
