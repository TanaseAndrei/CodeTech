package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.model.projection.CourseCoverImage;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.repository.LectureRepositoryGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

    private static final String THE_SELECTED_COURSE_DOES_NOT_EXIST = "The selected course does not exist!";

    private final CourseRepositoryGateway courseRepositoryGateway;
    private final LectureRepositoryGateway lectureRepositoryGateway;

    public Course saveOrUpdate(Course course) {
        return courseRepositoryGateway.saveOrUpdate(course);
    }

    public void enableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(true);
        courseRepositoryGateway.saveOrUpdate(course);
    }

    public void disableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        course.setAvailable(false);
        courseRepositoryGateway.saveOrUpdate(course);
    }

    public Course findById(Long id) {
        return courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
    }

    public Course findByIdAndUsername(Long id, String username) {
        return courseRepositoryGateway.findByIdAndUsername(id, username)
                .orElseThrow(() -> new AppException("The course with id " + id
                        + " does not exist in the portfolio of instructor " + username, HttpStatus.NOT_FOUND));
    }

    //TODO paginatation and filtering
    public List<Course> getAll() {
        return courseRepositoryGateway.findAll();
    }

    @Transactional
    public List<String> delete(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(THE_SELECTED_COURSE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        List<String> videoNames = lectureRepositoryGateway.getLectureVideos(id);
        List<Lecture> lectures = lectureRepositoryGateway.getLecturesByCourseId(course.getId());
        List<String> lectureFiles = collectAllLectureFiles(lectures);
        courseRepositoryGateway.deleteById(id);
        return collectAllFilesToDelete(course.getCoverImageName(), videoNames, lectureFiles);
    }

    public boolean courseExistsByName(String name) {
        return courseRepositoryGateway.courseExistsByName(name);
    }

    public String getCourseFolderName(Long id) {
        return courseRepositoryGateway.getCourseFolderName(id)
                .orElseThrow(() -> new AppException("The course does not have an associated folder!", HttpStatus.NOT_FOUND));
    }

    public boolean hasQuiz(Long id) {
        Course course = findById(id);
        return course.getQuiz() != null;
    }

    public CourseCoverImage getCourseCoverById(Long id) {
        return courseRepositoryGateway.getCourseCoverById(id)
                .orElseThrow(() -> new AppException("The course with id " + id + " does not contain a cover", HttpStatus.NOT_FOUND));
    }

    public boolean containsCourseCover(Long id) {
        String coverImageName = findById(id).getCoverImageName();
        return StringUtils.isEmpty(coverImageName);
    }

    private List<String> collectAllLectureFiles(List<Lecture> lectures) {
        return lectures.stream().map(Lecture::getLectureFileNames).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<String> collectAllFilesToDelete(String coverImageName, List<String> videoNames, List<String> lectureFiles) {
        return Stream.of(Collections.singletonList(coverImageName), lectureFiles, videoNames).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
