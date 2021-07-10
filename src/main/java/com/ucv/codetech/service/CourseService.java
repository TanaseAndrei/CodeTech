package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
import com.ucv.codetech.repository.CourseRepositoryGateway;
import com.ucv.codetech.service.converter.CourseConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CategoryRepositoryGateway categoryRepositoryGateway;
    private final CourseRepositoryGateway courseRepositoryGateway;
    private final CourseConverter courseConverter;
    private final FileService fileService;

    @Transactional
    public Long createCourse(CourseDto courseDto, MultipartFile multipartFile) {
        try {
            Category category = categoryRepositoryGateway.findById(courseDto.getCategoryId()).orElseThrow(RuntimeException::new);
            String coverPath = fileService.moveCoverFile(multipartFile, courseDto.getName());
            Course course = courseConverter.courseDtoToCourse(courseDto);
            course.setCoverImagePath(coverPath);
            course.setCategory(category);
            return courseRepositoryGateway.save(course).getId();
        } catch (IOException ioException) {
            throw new AppException("An error has occurred while trying to create the cover for the course", HttpStatus.BAD_REQUEST);
        }
    }

    public Course getById(Long id) {
        return courseRepositoryGateway.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Course> getAll() {
        return courseRepositoryGateway.findAll();
    }

    public void deleteCourse(Long id) {
        courseRepositoryGateway.deleteById(id);
    }
}
