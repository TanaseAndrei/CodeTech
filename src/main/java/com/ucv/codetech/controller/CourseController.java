package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.facade.CourseFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCourse(@RequestBody CourseDto courseDto) {
        return courseFacade.createCourse(courseDto);
    }

    @PatchMapping(path = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void uploadCourseCover(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
        courseFacade.addCourseCover(multipartFile, id);
    }

    @PatchMapping(path = "/{id}/enable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enableCourse(@PathVariable Long id) {
        courseFacade.enableCourse(id);
    }

    @PatchMapping(path = "/{id}/disable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void disableCourse(@PathVariable Long id) {
        courseFacade.disableCourse(id);
    }

    @PatchMapping(path = "/{id}/lectures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void uploadCourseLectures(@PathVariable("id") Long courseId, @ModelAttribute LectureDto lectureDto) {
        courseFacade.createLecture(courseId, lectureDto);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullDisplayCourseDto getCourse(@PathVariable("id") Long id) {
        FullDisplayCourseDto fullDisplayCourseDto = courseFacade.getById(id);
        fullDisplayCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(fullDisplayCourseDto.getName(),
                fullDisplayCourseDto.getCoverImageName())).withRel(LinkRelation.of("cover")));
        for (DisplayLectureDto displayLectureDto : fullDisplayCourseDto.getDisplayLectureDtos()) {
            for (String lectureFileName : displayLectureDto.getLectureFileNames()) {
                displayLectureDto.add(linkTo(methodOn(LectureController.class).downloadFile(displayLectureDto.getId(), lectureFileName)).withRel(LinkRelation.of("file")));
            }
            displayLectureDto.add(linkTo(methodOn(LectureController.class).downloadFile(displayLectureDto.getId(), displayLectureDto.getLectureVideoName())).withRel(LinkRelation.of("video")));
        }
        return fullDisplayCourseDto;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DisplayCourseDto> getAllCourses() {
        List<DisplayCourseDto> displayCourseDtos = courseFacade.getAll();
        for (DisplayCourseDto displayCourseDto : displayCourseDtos) {
            displayCourseDto.add(linkTo(methodOn(CourseController.class).getCourse(displayCourseDto.getId())).withSelfRel());
            displayCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(displayCourseDto.getName(), displayCourseDto.getCoverImageName())).withRel("src"));
        }
        return displayCourseDtos;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long id) {
        courseFacade.deleteCourse(id);
    }
}
