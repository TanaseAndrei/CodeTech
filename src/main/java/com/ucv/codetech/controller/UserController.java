package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping(path = "/student/register")
    public Long registerStudent(@RequestBody StudentDto studentDto) {
       return userFacade.registerStudent(studentDto);
    }

    @PostMapping(path = "/instructor/register")
    public Long registerInstructor(@RequestBody InstructorDto instructorDto) {
        return userFacade.registerInstructor(instructorDto);
    }

    //TODO verificari pentru student si instructor
    //TODO login with jwt
}
