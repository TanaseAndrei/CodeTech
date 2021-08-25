package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.controller.swagger.RegisterApi;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController implements RegisterApi {

    private final UserFacade userFacade;

    @PostMapping(path = "/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerStudent(@Valid @RequestBody StudentDto studentDto) {
        return userFacade.registerStudent(studentDto);
    }

    @PostMapping(path = "/instructor")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerInstructor(@Valid @RequestBody InstructorDto instructorDto) {
        return userFacade.registerInstructor(instructorDto);
    }
}
