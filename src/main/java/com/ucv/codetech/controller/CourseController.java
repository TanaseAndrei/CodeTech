package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CommentDto;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import com.ucv.codetech.controller.swagger.CourseApi;
import com.ucv.codetech.facade.CourseFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
@Slf4j
public class CourseController implements CourseApi {

    private final CourseFacade courseFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCourse(@Valid @RequestBody CourseDto courseDto, Principal principal) {
        return courseFacade.createCourse(courseDto, principal.getName());
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void uploadCourseCover(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
        courseFacade.addCourseCover(multipartFile, id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}/cover")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseCover(@PathVariable("id") Long id) {
        courseFacade.deleteCourseCover(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long addComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto, Principal principal) {
        return courseFacade.addComment(id, commentDto, principal.getName());
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/lectures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long uploadLecture(@PathVariable("id") Long courseId, @ModelAttribute LectureDto lectureDto) {
        return courseFacade.createLecture(courseId, lectureDto);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/quiz", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createQuiz(@PathVariable("id") Long id, @RequestBody QuizDto quizDto, Principal principal) {
        return courseFacade.createQuiz(id, quizDto, principal.getName());
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping(path = "/{id}/enable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enableCourse(@PathVariable Long id) {
        courseFacade.enableCourse(id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping(path = "/{id}/disable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void disableCourse(@PathVariable Long id) {
        courseFacade.disableCourse(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping(path = "/{id}/enroll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollToCourse(@PathVariable("id") Long id, Principal principal) {
        log.info("Student {} is enrolling into course with id {}", principal.getName(), id);
        courseFacade.enrollToCourse(id, principal.getName());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PreviewFullCourseDto getCourse(@PathVariable("id") Long id) {
        PreviewFullCourseDto previewFullCourseDto = courseFacade.getById(id);
        addHateoasFullCourseCoverImage(previewFullCourseDto);
        return previewFullCourseDto;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PreviewCourseDto> getAllCourses(Principal principal) {
        List<PreviewCourseDto> previewCourseDtos = courseFacade.getAll(principal.getName());
        for (PreviewCourseDto previewCourseDto : previewCourseDtos) {
            addHateoasCourseSelfRel(previewCourseDto);
            addHateoasDisplayCourse(previewCourseDto);
        }
        return previewCourseDtos;
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long id) {
        courseFacade.deleteCourse(id);
    }

    private void addHateoasCourseSelfRel(PreviewCourseDto previewCourseDto) {
        previewCourseDto.add(linkTo(methodOn(CourseController.class).getCourse(previewCourseDto.getId())).withSelfRel());
    }

    private void addHateoasDisplayCourse(PreviewCourseDto previewCourseDto) {
        if (previewCourseDto.getCoverImageName() != null) {
            previewCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(previewCourseDto.getName(), previewCourseDto.getCoverImageName())).withRel("preview"));
        }
    }

    private void addHateoasFullCourseCoverImage(PreviewFullCourseDto previewFullCourseDto) {
        if (previewFullCourseDto.getCoverImageName() != null) {
            previewFullCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(previewFullCourseDto.getName(),
                    previewFullCourseDto.getCoverImageName())).withRel(LinkRelation.of("cover-image")));
        }
    }
}
