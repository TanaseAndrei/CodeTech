package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.output.InstructorFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewCourseDisplayDto;
import com.ucv.codetech.controller.model.output.InstructorPreviewQuizDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Api(value = "The instructor API")
public interface InstructorApi {

    List<InstructorPreviewCourseDisplayDto> getCourses(Principal principal);

    List<InstructorPreviewQuizDto> getQuiz(Principal principal);

    InstructorFullCourseDisplayDto getCourse(@PathVariable("username") String username, @PathVariable("id") Long courseId);
}
