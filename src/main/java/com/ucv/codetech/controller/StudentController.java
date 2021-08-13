package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.*;
import com.ucv.codetech.controller.swagger.StudentApi;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/students")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
public class StudentController implements StudentApi {

    private final UserFacade userFacade;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCourseDisplayDto> getUsersCourses(Principal principal) {
        List<StudentCourseDisplayDto> enrolledCourses = userFacade.getEnrolledCourses(principal.getName());
        addHateoasForEnrolledCourses(enrolledCourses, principal.getName());
        return enrolledCourses;
    }

    @GetMapping(path = "/certifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCertificationDisplayDto> getCertifications(Principal principal) {
        List<StudentCertificationDisplayDto> certifications = userFacade.getCertifications(principal.getName());
        addHateoasForCertifications(certifications, principal.getName());
        return certifications;
    }

    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        userFacade.completeLecture(id);
    }

    @GetMapping(path = "/{username}/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentFullCourseDisplayDto getUsersCourse(@PathVariable("id") Long id, @PathVariable("username") String username) {
        StudentFullCourseDisplayDto enrolledCourse = userFacade.getEnrolledCourse(username, id);
        enrolledCourse.add(linkTo(methodOn(MediaController.class).getFileAsResource(enrolledCourse.getName(), enrolledCourse.getCoverImageName())).withRel("src"));
        addHateoasForLectures(enrolledCourse.getLectureWrapperDisplayDtos());
        return enrolledCourse;
    }

    private void addHateoasForCertifications(List<StudentCertificationDisplayDto> certifications, String username) {
        for (StudentCertificationDisplayDto certification : certifications) {
            certification.add(linkTo(methodOn(StudentController.class).getUsersCourse(certification.getCourseId(), username)).withRel(LinkRelation.of("enrolled-course")));
        }
    }

    private void addHateoasForEnrolledCourses(List<StudentCourseDisplayDto> enrolledCourses, String name) {
        for (StudentCourseDisplayDto enrolledCourse : enrolledCourses) {
            enrolledCourse.add(linkTo(methodOn(StudentController.class).getUsersCourse(enrolledCourse.getEnrolledCourseId(), name)).withRel(LinkRelation.of("enrolled-course")));
        }
    }

    private void addHateoasForLectures(List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos) {
        for (StudentFullLectureWrapperDisplayDto lectureWrapperDisplayDto : lectureWrapperDisplayDtos) {
            lectureWrapperDisplayDto.add(linkTo(methodOn(LectureController.class).zipLectureFiles(lectureWrapperDisplayDto.getLectureId())).withRel(LinkRelation.of("zip-lectures")));
            for (String lectureFileName : lectureWrapperDisplayDto.getLectureFileNames()) {
                lectureWrapperDisplayDto.add(linkTo(methodOn(LectureController.class).downloadFile(lectureWrapperDisplayDto.getLectureId(), lectureFileName)).withRel(LinkRelation.of("download-file")));
            }
            lectureWrapperDisplayDto.add(linkTo(methodOn(LectureController.class).downloadFile(lectureWrapperDisplayDto.getLectureId(), lectureWrapperDisplayDto.getLectureVideoName())).withRel("video"));
        }
    }
}
