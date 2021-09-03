package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

    private static final String THE_SELECTED_COURSE_DOES_NOT_EXIST = "The selected course does not exist!";

    private final CourseRepositoryGateway courseRepositoryGateway;

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
                        + " does not exist in the portfolio of the instructor " + username, HttpStatus.NOT_FOUND));
    }

    public List<Course> getAll() {
        return courseRepositoryGateway.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        courseRepositoryGateway.deleteById(id);
    }

    public boolean courseExistsByName(String name) {
        return courseRepositoryGateway.courseExistsByName(name);
    }

    public String getCourseFolderName(Long id) {
        return courseRepositoryGateway.getCourseFolderName(id)
                .orElseThrow(() -> new AppException("The course with the id " + id + " does not exit or does not have" +
                        " an associated folder!", HttpStatus.NOT_FOUND));
    }

    public boolean hasQuiz(Long id) {
        Course course = findById(id);
        return course.getQuiz() != null;
    }

    public boolean containsCourseCover(Long id) {
        String coverImageName = findById(id).getCoverImageName();
        return !StringUtils.isEmpty(coverImageName);
    }
}
