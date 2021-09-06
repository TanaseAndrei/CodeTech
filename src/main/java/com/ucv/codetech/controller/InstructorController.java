package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.InstructorFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewQuizDto;
import com.ucv.codetech.controller.swagger.InstructorApi;
import com.ucv.codetech.facade.AuthenticationFacade;
import com.ucv.codetech.facade.CourseFacade;
import com.ucv.codetech.service.MediaUrlService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/instructor/me")
@PreAuthorize("hasRole('INSTRUCTOR')")
@AllArgsConstructor
public class InstructorController implements InstructorApi {

    private final AuthenticationFacade authenticationFacade;
    private final CourseFacade courseFacade;
    private final MediaUrlService mediaUrlService;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstructorPreviewCourseDisplayDto> getCourses() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<InstructorPreviewCourseDisplayDto> instructorsCourses = courseFacade.getInstructorsCourses(currentLoggedUser);
        addHateoasForInstructorCourses(instructorsCourses);
        return instructorsCourses;
    }

    @GetMapping(path = "/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstructorPreviewQuizDto> getQuiz() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<InstructorPreviewQuizDto> quizzes = courseFacade.getQuizzes(currentLoggedUser);
        addHateoasForInstructorPreviewQuizDto(quizzes);
        return quizzes;
    }

    @GetMapping(path = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstructorFullCourseDisplayDto getCourse(@PathVariable("id") Long courseId) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        InstructorFullCourseDisplayDto instructorCourse = courseFacade.getInstructorCourse(currentLoggedUser, courseId);
        addHateoasForInstructorFullCourseDisplayDto(instructorCourse);
        return instructorCourse;
    }

    private void addHateoasForInstructorFullCourseDisplayDto(InstructorFullCourseDisplayDto instructorCourse) {
        instructorCourse.add(mediaUrlService.getLinkForGettingMedia(instructorCourse.getName(), instructorCourse.getCoverImageName(), "cover-image"));
    }

    private void addHateoasForInstructorPreviewQuizDto(List<InstructorPreviewQuizDto> quizzes) {
        for (InstructorPreviewQuizDto quiz : quizzes) {
            quiz.add(linkTo(methodOn(QuizController.class).getQuiz(quiz.getQuizId())).withRel(LinkRelation.of("quiz")));
        }
    }

    private void addHateoasForInstructorCourses(List<InstructorPreviewCourseDisplayDto> instructorsCourses) {
        for (InstructorPreviewCourseDisplayDto instructorsCourse : instructorsCourses) {
            instructorsCourse.add(linkTo(methodOn(CourseController.class).getCourse(instructorsCourse.getCourseId())).withRel(LinkRelation.of("instructor-course")));
            if(StringUtils.isNotEmpty(instructorsCourse.getCoverImage())) {
                instructorsCourse.add(mediaUrlService.getLinkForGettingMedia(instructorsCourse.getName(), instructorsCourse.getCoverImage(), "cover-image"));
            }
        }
    }
}
