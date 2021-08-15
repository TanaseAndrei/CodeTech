package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.output.InstructorPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullLectureWrapperDisplayDto;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.model.LectureWrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnrolledCourseConverter {

    public List<StudentPreviewCourseDisplayDto> entitiesToStudentCourseDisplayDtos(List<EnrolledCourse> enrolledCourses) {
        return enrolledCourses
                .stream()
                .map(this::entityToStudentCourseDisplayDto)
                .collect(Collectors.toList());
    }

    public StudentPreviewCourseDisplayDto entityToStudentCourseDisplayDto(EnrolledCourse enrolledCourse) {
        StudentPreviewCourseDisplayDto studentPreviewCourseDisplayDto = new StudentPreviewCourseDisplayDto();
        studentPreviewCourseDisplayDto.setEnrolledCourseId(enrolledCourse.getId());
        studentPreviewCourseDisplayDto.setCourseCompleted(enrolledCourse.isCourseCompleted());
        studentPreviewCourseDisplayDto.setName(enrolledCourse.getCourse().getName());
        studentPreviewCourseDisplayDto.setNumberOfCompletedLectures(enrolledCourse.getNumberOfCompletedLectures());
        studentPreviewCourseDisplayDto.setNumberOfLectures(enrolledCourse.getNumberOfLectures());
        studentPreviewCourseDisplayDto.setCoverImageName(enrolledCourse.getCourse().getCoverImageName());
        return studentPreviewCourseDisplayDto;
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

    public List<InstructorPreviewCourseDisplayDto> entitiesToInstructorCourseDisplayDtos(List<Course> courses) {
        return courses
                .stream()
                .map(this::entityToInstructorCourseDisplayDto)
                .collect(Collectors.toList());
    }

    public InstructorPreviewCourseDisplayDto entityToInstructorCourseDisplayDto(Course course) {
        InstructorPreviewCourseDisplayDto instructorPreviewCourseDisplayDto = new InstructorPreviewCourseDisplayDto();
        instructorPreviewCourseDisplayDto.setCourseId(course.getId());
        instructorPreviewCourseDisplayDto.setName(course.getName());
        instructorPreviewCourseDisplayDto.setCoverImage(course.getCoverImageName());
        instructorPreviewCourseDisplayDto.setNumberOfLectures(course.getNumberOfLectures());
        instructorPreviewCourseDisplayDto.setNumberOfComments(course.getNumberOfComments());
        instructorPreviewCourseDisplayDto.setNumberOfEnrolledStudents(course.getNrOfEnrolledStudents());
        return instructorPreviewCourseDisplayDto;
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
