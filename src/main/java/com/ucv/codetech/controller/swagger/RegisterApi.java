package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The register API")
public interface RegisterApi {

    Long registerStudent(@Valid @RequestBody StudentDto studentDto);

    Long registerInstructor(@Valid @RequestBody InstructorDto instructorDto);
}
