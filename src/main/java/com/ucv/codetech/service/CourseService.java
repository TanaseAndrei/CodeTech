package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.controller.model.CourseLectureDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.CourseLecture;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.service.converter.CourseConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CategoryRepositoryGateway categoryRepositoryGateway;
    private final CourseRepositoryGateway courseRepositoryGateway;
    private final CourseConverter courseConverter;
    private final FileService fileService;

    @Transactional
    public Long createCourse(CourseDto courseDto) {
        Category category = categoryRepositoryGateway.findById(courseDto.getCategoryId())
                .orElseThrow(() -> new AppException("The selected category doesn't exist!", HttpStatus.NOT_FOUND));
        Course course = courseConverter.courseDtoToCourse(courseDto);
        course.setCategory(category);
        return courseRepositoryGateway.save(course).getId();
    }

    @Transactional
    public void addCourseCover(MultipartFile multipartFile, Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException("The selected course doesn't exist!", HttpStatus.NOT_FOUND));
            String coverPath = fileService.moveCoverFile(multipartFile, course.getName());
            course.setCoverImagePath(coverPath);
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public void enableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
        course.setAvailable(true);
        courseRepositoryGateway.save(course);
    }

    public void disableCourse(Long id) {
        Course course = courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
        course.setAvailable(false);
        courseRepositoryGateway.save(course);
    }

    public Course getById(Long id) {
        return courseRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
    }

    public List<Course> getAll() {
        return courseRepositoryGateway.findAll();
    }

    public void deleteCourse(Long id) {
        try {
            Course course = courseRepositoryGateway.findById(id)
                    .orElseThrow(() -> new AppException("The selected category doesn't exist!", HttpStatus.NOT_FOUND));
            fileService.deleteCover(course.getCoverImagePath());
            courseRepositoryGateway.deleteById(id);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void createCourseLecture(Long courseId, CourseLectureDto courseLectureDto) {
        try {
            Course course = courseRepositoryGateway.findById(courseId)
                    .orElseThrow(() -> new AppException("The selected course does not exist!", HttpStatus.NOT_FOUND));
            String videoName = fileService.moveVideoLecture(courseLectureDto.getLectureVideo(), courseLectureDto.getName());
            CourseLecture courseLecture = new CourseLecture();
            courseLecture.setName(courseLectureDto.getName());
            courseLecture.setDescription(courseLectureDto.getDescription());
            courseLecture.setLectureFilePaths(Collections.emptyList());
            courseLecture.setLectureVideoPath(videoName);
            course.getCourseLectures().add(courseLecture);
            courseRepositoryGateway.save(course);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
