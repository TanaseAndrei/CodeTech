package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.*;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(value = "The course API")
public interface CourseApi {

    Long createCourse(@Valid @RequestBody CourseDto courseDto);

    void updateCourse(@PathVariable("id") Long id, @Valid @RequestBody UpdateCourseDto updateCourseDto);

    void uploadCourseCover(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id);

    void deleteCourseCover(@PathVariable("id") Long id);

    Long addComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto);

    Long uploadLecture(@PathVariable("id") Long courseId, @Valid @ModelAttribute LectureDto lectureDto);

    Long createQuiz(@PathVariable("id") Long id, @Valid @RequestBody QuizDto quizDto);

    void enableCourse(@PathVariable Long id);

    void disableCourse(@PathVariable Long id);

    void enrollToCourse(@PathVariable("id") Long id);

    PreviewFullCourseDto getCourse(@PathVariable("id") Long id);

    List<PreviewCourseDto> getAllCourses();

    void deleteCourse(@PathVariable("id") Long id);
}
