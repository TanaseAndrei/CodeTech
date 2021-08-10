package com.ucv.codetech.controller;

import com.ucv.codetech.controller.swagger.UserApi;
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
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserFacade userFacade;

    //TODO enroll course, complete courses lecture, take quiz, get certification after quiz is completed, add course to favourite

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrolledCourse> getUsersCourses(Principal principal) {
        log.info("Student {} is retrieving his enrolled courses", principal.getName());
        return userFacade.getEnrolledCourses(principal.getName());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        //TODO complete lecture wrapper
    }
}
