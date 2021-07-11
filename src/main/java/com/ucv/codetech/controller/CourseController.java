package com.ucv.codetech.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //creare curs
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCourse(@RequestBody CourseDto courseDto) {
        Long courseId = courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseId);
    }

    //upload cover image pentru curs
    @PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCourseCover(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
        courseService.addCourseCover(multipartFile, id);
    }

    //face cursul available
    @PatchMapping(path = "/{id}/enable")
    public ResponseEntity<Object> enableCourse(@PathVariable Long id) {
        courseService.enableCourse(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //face cursul unavailable
    @PatchMapping(path = "/{id}/disable")
    public ResponseEntity<Object> disableCourse(@PathVariable Long id) {
        courseService.disableCourse(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //extrage un curs cu tot ce contine
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Course getCourse(@PathVariable("id") Long id) {
        return courseService.getById(id);
    }

    //extrage toate cursurile
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getAllCourses() {
        return courseService.getAll();
    }

    //sterge un curs
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
