package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.*;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "The course API")
public interface CourseApi {

    @ApiOperation(value = "Create a new course", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a new course"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    Long createCourse(CourseDto courseDto);

    @ApiOperation(value = "Update a new course", httpMethod = "PATCH", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated a course"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void updateCourse(@Schema(description = "The id of the course", example = "1") Long id, UpdateCourseDto updateCourseDto);

    @ApiOperation(value = "Upload the cover image of a course", httpMethod = "POST", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully uploaded a course's cover image"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void uploadCourseCover(MultipartFile multipartFile, @Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Delete the cover image of a course", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted course's cover image"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void deleteCourseCover(@Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Add a comment to a course", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added a comment"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    Long addComment(@Schema(description = "The id of the course", example = "1") Long id, CommentDto commentDto);

    @ApiOperation(value = "Add a lecture to a course", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted course's cover image"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    Long uploadLecture(@Schema(description = "The id of the course", example = "1") Long courseId, LectureDto lectureDto);

    @ApiOperation(value = "Create the quiz of a course", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create course's quiz"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    Long createQuiz(@Schema(description = "The id of the course", example = "1") Long id, QuizDto quizDto);

    @ApiOperation(value = "Enable a course", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully enabled a course"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void enableCourse(@Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Disable a course", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully disabled a course"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void disableCourse(@Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Enroll to a course", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully enrolled a course"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void enrollToCourse(@Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Retrieve a full course", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = PreviewFullCourseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully retrieved a course"),
            @ApiResponse(code = 404, message = "The course does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    PreviewFullCourseDto getCourse(@Schema(description = "The id of the course", example = "1") Long id);

    @ApiOperation(value = "Retrieve all the available courses", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE,
            response = PreviewCourseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all the courses"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    List<PreviewCourseDto> getAllCourses();

    @ApiOperation(value = "Retrieve all the available courses", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted a course"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void deleteCourse(@Schema(description = "The id of the course", example = "1") Long id);
}
