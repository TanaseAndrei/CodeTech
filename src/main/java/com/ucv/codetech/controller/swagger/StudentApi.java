package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentPreviewCourseDisplayDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(value = "The student API")
public interface StudentApi {

    List<StudentPreviewCourseDisplayDto> getCourses();

    List<StudentCertificationDisplayDto> getCertifications();

    void completeLecture(@PathVariable("id") Long id);

    StudentFullCourseDisplayDto getCourse(@PathVariable("id") Long id);
}
