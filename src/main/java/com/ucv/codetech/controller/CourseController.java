package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.*;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import com.ucv.codetech.controller.swagger.CourseApi;
import com.ucv.codetech.facade.AuthenticationFacade;
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
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
@Slf4j
public class CourseController implements CourseApi {

    private final CourseFacade courseFacade;
    private final AuthenticationFacade authenticationFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCourse(@Valid @RequestBody CourseDto courseDto) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        log.info("Current user is: " + currentLoggedUser);
        return courseFacade.createCourse(courseDto, currentLoggedUser);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(@PathVariable("id") Long id, @RequestBody UpdateCourseDto updateCourseDto) {
        courseFacade.updateCourse(id, updateCourseDto);
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
    public Long addComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        return courseFacade.addComment(id, commentDto, currentLoggedUser);
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
    public Long createQuiz(@PathVariable("id") Long id, @RequestBody QuizDto quizDto) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        return courseFacade.createQuiz(id, quizDto, currentLoggedUser);
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
    public void enrollToCourse(@PathVariable("id") Long id) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        courseFacade.enrollToCourse(id, currentLoggedUser);
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
    public List<PreviewCourseDto> getAllCourses() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<PreviewCourseDto> previewCourseDtos = courseFacade.getAll(currentLoggedUser);
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
            previewCourseDto.add(linkTo(methodOn(MediaController.class).getFile(previewCourseDto.getName(), previewCourseDto.getCoverImageName())).withRel("preview"));
        }
    }

    private void addHateoasFullCourseCoverImage(PreviewFullCourseDto previewFullCourseDto) {
        if (previewFullCourseDto.getCoverImageName() != null) {
            previewFullCourseDto.add(linkTo(methodOn(MediaController.class).getFile(previewFullCourseDto.getName(),
                    previewFullCourseDto.getCoverImageName())).withRel(LinkRelation.of("cover-image")));
        }
    }
}
