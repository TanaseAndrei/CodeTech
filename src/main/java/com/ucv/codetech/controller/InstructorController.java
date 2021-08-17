package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.InstructorFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewQuizDto;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/instructor/me")
@PreAuthorize("hasRole('INSTRUCTOR')")
@AllArgsConstructor
public class InstructorController {

    private final UserFacade userFacade;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstructorPreviewCourseDisplayDto> getCourses(Principal principal) {
        List<InstructorPreviewCourseDisplayDto> instructorsCourses = userFacade.getInstructorsCourses(principal.getName());
        addHateoasForInstructorCourses(instructorsCourses);
        return instructorsCourses;
    }

    @GetMapping(path = "/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstructorPreviewQuizDto> getQuiz(Principal principal) {
        List<InstructorPreviewQuizDto> quizzes = userFacade.getQuizzes(principal.getName());
        addHateoasForInstructorPreviewQuizDto(quizzes);
        return quizzes;
    }

    @GetMapping(path = "/{username}/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstructorFullCourseDisplayDto getCourse(@PathVariable("username") String username, @PathVariable("id") Long courseId) {
        InstructorFullCourseDisplayDto instructorCourse = userFacade.getInstructorCourse(username, courseId);
        addHateoasForInstructorFullCourseDisplayDto(instructorCourse);
        return instructorCourse;
    }

    private void addHateoasForInstructorFullCourseDisplayDto(InstructorFullCourseDisplayDto instructorCourse) {
        instructorCourse.add(linkTo(methodOn(MediaController.class).getFile(instructorCourse.getName(), instructorCourse.getCoverImageName())).withRel(LinkRelation.of("cover-image")));
    }

    private void addHateoasForInstructorPreviewQuizDto(List<InstructorPreviewQuizDto> quizzes) {
        for (InstructorPreviewQuizDto quiz : quizzes) {
            quiz.add(linkTo(methodOn(QuizController.class).getQuiz(quiz.getQuizId())).withRel(LinkRelation.of("quiz")));
        }
    }

    private void addHateoasForInstructorCourses(List<InstructorPreviewCourseDisplayDto> instructorsCourses) {
        for (InstructorPreviewCourseDisplayDto instructorsCourse : instructorsCourses) {
            instructorsCourse.add(linkTo(methodOn(CourseController.class).getCourse(instructorsCourse.getCourseId())).withRel(LinkRelation.of("instructor-course")));
            instructorsCourse.add(linkTo(methodOn(MediaController.class).getFile(instructorsCourse.getName(), instructorsCourse.getCoverImage())).withRel(LinkRelation.of("cover-image")));
        }
    }
}
