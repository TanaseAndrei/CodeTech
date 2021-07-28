package com.ucv.codetech.controller;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.CourseLectureDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.DisplayLectureDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping(path = "/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCourse(@RequestBody CourseDto courseDto) {
        Long courseId = courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseId);
    }

    @PatchMapping(path = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void uploadCourseCover(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
        courseService.addCourseCover(multipartFile, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullDisplayCourseDto getCourse(@PathVariable("id") Long id) {
        FullDisplayCourseDto fullDisplayCourseDto = courseService.getById(id);
        fullDisplayCourseDto.add(linkTo(methodOn(CourseController.class).getFileAsResource(fullDisplayCourseDto.getName(),
                fullDisplayCourseDto.getCoverImageName())).withRel(LinkRelation.of("cover")));
        for (DisplayLectureDto displayLectureDto : fullDisplayCourseDto.getDisplayLectureDtos()) {
            for (String lectureFileName : displayLectureDto.getLectureFileNames()) {
                displayLectureDto.add(linkTo(methodOn(CourseController.class).getFileAsResource(fullDisplayCourseDto.getName(), lectureFileName)).withRel(LinkRelation.of("file")));
            }
            displayLectureDto.add(linkTo(methodOn(CourseController.class).getFileAsResource(fullDisplayCourseDto.getName(), displayLectureDto.getLectureVideoName())).withRel(LinkRelation.of("video")));
        }
        return fullDisplayCourseDto;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DisplayCourseDto> getAllCourses() {
        List<DisplayCourseDto> displayCourseDtos = courseService.getAll();
        for (DisplayCourseDto displayCourseDto : displayCourseDtos) {
            displayCourseDto.add(linkTo(methodOn(CourseController.class).getCourse(displayCourseDto.getId())).withSelfRel());
            displayCourseDto.add(linkTo(methodOn(CourseController.class).getFileAsResource(displayCourseDto.getName(), displayCourseDto.getCoverImageName())).withRel("src"));
        }
        return displayCourseDtos;
    }

    @PatchMapping(path = "/{id}/enable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enableCourse(@PathVariable Long id) {
        courseService.enableCourse(id);
    }

    @PatchMapping(path = "/{id}/disable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void disableCourse(@PathVariable Long id) {
        courseService.disableCourse(id);
    }

    @PatchMapping(path = "/{id}/lectures", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void uploadCourseLectures(@PathVariable("id") Long courseId, @ModelAttribute CourseLectureDto courseLectureDto) {
        courseService.createCourseLecture(courseId, courseLectureDto);
    }

    @PatchMapping(path = "/{courseId}/lectures/{lectureId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void uploadLectureFiles(@PathVariable("courseId") Long courseId, @PathVariable("lectureId") Long lectureId,
                                   @RequestParam("files") MultipartFile[] multipartFiles) {
        courseService.addCourseLectureFiles(courseId, lectureId, multipartFiles);
    }

    @GetMapping(path = "/{courseId}/lectures/{lectureId}/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("courseId") Long courseId,
                                                 @PathVariable("lectureId") Long lectureId,
                                                 @PathVariable("fileName") String fileName) {
        Resource resource = courseService.downloadFile(courseId, lectureId, fileName);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @GetMapping(path = "/{courseId}/lectures/{lectureId}/zip-files")
    public ResponseEntity<Resource> zipLectureFiles(@PathVariable("courseId") Long courseId,
                                                 @PathVariable("lectureId") Long lectureId) {
        Resource resource = courseService.zipLectureFiles(courseId, lectureId);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @GetMapping(path = "/{courseName}/media/{filename}")
    public ResponseEntity<Resource> getFileAsResource(@PathVariable("courseName") String courseName,
                                                      @PathVariable("filename") String filename) {
        Resource resource = courseService.getMediaAsResource(courseName, filename);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    private HttpHeaders createHeader(Resource resource) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        return httpHeaders;
    }

}
