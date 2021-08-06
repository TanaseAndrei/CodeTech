package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CommentDto;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.controller.swagger.CourseApi;
import com.ucv.codetech.facade.CourseFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
public class CourseController implements CourseApi {

    private final CourseFacade courseFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCourse(@Valid @RequestBody CourseDto courseDto) {
        return courseFacade.createCourse(courseDto);
    }

    @PostMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        courseFacade.addComment(id, commentDto);
    }

    @PostMapping(path = "/{id}/lectures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void uploadLectures(@PathVariable("id") Long courseId, @ModelAttribute LectureDto lectureDto) {
        courseFacade.createLecture(courseId, lectureDto);
    }

    @PostMapping(path = "/{id}/quiz", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createQuiz(@PathVariable("id") Long id, @RequestBody QuizDto quizDto) {
        courseFacade.createQuiz(id, quizDto);
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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullDisplayCourseDto getCourse(@PathVariable("id") Long id) {
        FullDisplayCourseDto fullDisplayCourseDto = courseFacade.getById(id);
        addHateoasFullCourseCoverImage(fullDisplayCourseDto);
        addHateoasLectures(fullDisplayCourseDto);
        addHateoasQuiz(fullDisplayCourseDto);
        return fullDisplayCourseDto;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DisplayCourseDto> getAllCourses() {
        List<DisplayCourseDto> displayCourseDtos = courseFacade.getAll();
        for (DisplayCourseDto displayCourseDto : displayCourseDtos) {
            addHateoasCourseSelfRel(displayCourseDto);
            addHateoasDisplayCourse(displayCourseDto);
        }
        return displayCourseDtos;
    }

    private void addHateoasDisplayCourse(DisplayCourseDto displayCourseDto) {
        if (displayCourseDto.getCoverImageName() != null) {
            displayCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(displayCourseDto.getName(), displayCourseDto.getCoverImageName())).withRel("src"));
        }
    }

    private void addHateoasCourseSelfRel(DisplayCourseDto displayCourseDto) {
        displayCourseDto.add(linkTo(methodOn(CourseController.class).getCourse(displayCourseDto.getId())).withSelfRel());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long id) {
        courseFacade.deleteCourse(id);
    }

    private void addHateoasQuiz(FullDisplayCourseDto fullDisplayCourseDto) {
        if (fullDisplayCourseDto.getQuizId() != null) {
            fullDisplayCourseDto.add(linkTo(methodOn(QuizController.class).getQuiz(fullDisplayCourseDto.getQuizId())).withRel(LinkRelation.of("quiz")));
        }
    }

    private void addHateoasLectures(FullDisplayCourseDto fullDisplayCourseDto) {
        for (DisplayLectureDto displayLectureDto : fullDisplayCourseDto.getDisplayLectureDtos()) {
            addHateoasFile(displayLectureDto);
            addHateoasVideo(displayLectureDto);
        }
    }

    private void addHateoasFile(DisplayLectureDto displayLectureDto) {
        for (String lectureFileName : displayLectureDto.getLectureFileNames()) {
            displayLectureDto.add(linkTo(methodOn(LectureController.class).downloadFile(displayLectureDto.getId(), lectureFileName)).withRel(LinkRelation.of("file")));
        }
    }

    private void addHateoasVideo(DisplayLectureDto displayLectureDto) {
        displayLectureDto.add(linkTo(methodOn(LectureController.class).downloadFile(displayLectureDto.getId(), displayLectureDto.getLectureVideoName())).withRel(LinkRelation.of("video")));
    }

    private void addHateoasFullCourseCoverImage(FullDisplayCourseDto fullDisplayCourseDto) {
        if (fullDisplayCourseDto.getCoverImageName() != null) {
            fullDisplayCourseDto.add(linkTo(methodOn(MediaController.class).getFileAsResource(fullDisplayCourseDto.getName(),
                    fullDisplayCourseDto.getCoverImageName())).withRel(LinkRelation.of("cover")));
        }
    }
}
