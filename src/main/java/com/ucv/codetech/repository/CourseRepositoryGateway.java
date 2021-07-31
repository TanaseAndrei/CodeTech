package com.ucv.codetech.repository;

import com.ucv.codetech.model.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CourseRepositoryGateway {

    private final CourseRepository courseRepository;

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course saveOrUpdate(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public Optional<String> getCourseFolderName(Long id) {
        return courseRepository.getCourseFolderName(id);
    }

    public Optional<String> getCourseFolderName(String name) {
        return courseRepository.getCourseFolderName(name);
    }

    public boolean courseExistsByName(String name) {
        return courseRepository.existsByName(name);
    }
}
