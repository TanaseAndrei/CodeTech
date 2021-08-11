package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullDisplayCourseDto;
import com.ucv.codetech.facade.UserFacade;
import com.ucv.codetech.model.EnrolledCourse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentController {

    private final UserFacade userFacade;

    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        userFacade.completeLecture(id);
    }

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCourseDisplayDto> getUsersCourses(Principal principal) {
        log.info("Student {} is retrieving his enrolled courses", principal.getName());
        return userFacade.getEnrolledCourses(principal.getName());
    }
}
