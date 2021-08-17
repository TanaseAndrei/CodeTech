package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.output.*;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Difficulty;
import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.model.LectureWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final CommentConverter commentConverter;
    private final LectureConverter lectureConverter;

    public Course dtoToEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setComments(Collections.emptyList());
        course.setLectures(Collections.emptyList());
        course.setDifficulty(Difficulty.getByName(courseDto.getDifficulty().name()));
        return course;
    }

    public List<PreviewCourseDto> courseListToDisplayCourseDtoList(List<Course> allCourses, List<Course> usersCourses) {
        return allCourses
                .stream()
                .map(course -> entityToDisplayCourseDto(course, usersCourses))
                .collect(Collectors.toList());
    }

    public PreviewCourseDto entityToDisplayCourseDto(Course course, List<Course> usersCourses) {
        PreviewCourseDto previewCourseDto = new PreviewCourseDto();
        previewCourseDto.setCoverImageName(course.getCoverImageName());
        previewCourseDto.setId(course.getId());
        previewCourseDto.setNumberOfLectures(course.getNumberOfLectures());
        previewCourseDto.setNumberOfComments(course.getNumberOfComments());
        previewCourseDto.setName(course.getName());
        previewCourseDto.setInstructorName(course.getInstructor().getUsername());
        previewCourseDto.setEnrolledStudents(course.getNrOfEnrolledStudents());
        previewCourseDto.setDifficulty(course.getDifficulty().toString());
        if(usersCourses.contains(course)) {
            previewCourseDto.setAlreadyEnrolled(true);
        }
        return previewCourseDto;
    }

    public PreviewFullCourseDto entityToFullDisplayCourseDto(Course course) {
        PreviewFullCourseDto previewFullCourseDto = new PreviewFullCourseDto();
        previewFullCourseDto.setNumberOfLectures(course.getLectures().size());
        previewFullCourseDto.setCoverImageName(course.getCoverImageName());
        previewFullCourseDto.setEnrolledStudents(course.getNrOfEnrolledStudents());
        previewFullCourseDto.setInstructorName(course.getInstructor().getUsername());
        previewFullCourseDto.setName(course.getName());
        previewFullCourseDto.setDescription(course.getDescription());
        previewFullCourseDto.setComments(commentConverter.entitiesToDisplayCommentDtos(course.getComments()));
        previewFullCourseDto.setDisplayLectureDtos(course.getLectures().stream().map(lectureConverter::entityToDisplayLectureDto).collect(Collectors.toList()));
        return previewFullCourseDto;
    }

    public InstructorFullCourseDisplayDto entityToInstructorFullCourseDisplayDto(Course course) {
        InstructorFullCourseDisplayDto instructorFullCourseDisplayDto = new InstructorFullCourseDisplayDto();
        instructorFullCourseDisplayDto.setCourseId(course.getId());
        instructorFullCourseDisplayDto.setName(course.getName());
        instructorFullCourseDisplayDto.setDescription(course.getDescription());
        instructorFullCourseDisplayDto.setEnrolledStudents(course.getNrOfEnrolledStudents());
        instructorFullCourseDisplayDto.setNumberOfLectures(course.getNumberOfLectures());
        instructorFullCourseDisplayDto.setComments(commentConverter.entitiesToDisplayCommentDtos(course.getComments()));
        instructorFullCourseDisplayDto.setCoverImageName(course.getCoverImageName());
        instructorFullCourseDisplayDto.setFullLectureDisplayDtos(lectureConverter.entitiesToInstructorFullLectureDisplayDtos(course.getLectures()));
        return instructorFullCourseDisplayDto;
    }

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
