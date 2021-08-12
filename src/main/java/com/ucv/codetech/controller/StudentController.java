package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.swagger.StudentApi;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
public class StudentController implements StudentApi {

    private final UserFacade userFacade;

    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        userFacade.completeLecture(id);
    }

    //TODO add hateoas support
    @GetMapping(path = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentFullCourseDisplayDto getUsersCourse(@PathVariable("id") Long id, Principal principal) {
        return userFacade.getEnrolledCourse(principal.getName(), id);
    }

    //TODO add hateoas support
    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCourseDisplayDto> getUsersCourses(Principal principal) {
        return userFacade.getEnrolledCourses(principal.getName());
    }
}
