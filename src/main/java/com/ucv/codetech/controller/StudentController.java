package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullLectureWrapperDisplayDto;
import com.ucv.codetech.controller.model.output.StudentPreviewCourseDisplayDto;
import com.ucv.codetech.controller.swagger.StudentApi;
import com.ucv.codetech.facade.AuthenticationFacade;
import com.ucv.codetech.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/student/me")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
public class StudentController implements StudentApi {

    private final UserFacade userFacade;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentPreviewCourseDisplayDto> getCourses() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<StudentPreviewCourseDisplayDto> enrolledCourses = userFacade.getEnrolledCourses(currentLoggedUser);
        addHateoasForEnrolledCourses(enrolledCourses,currentLoggedUser);
        return enrolledCourses;
    }

    @GetMapping(path = "/certifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCertificationDisplayDto> getCertifications() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<StudentCertificationDisplayDto> certifications = userFacade.getCertifications(currentLoggedUser);
        addHateoasForCertifications(certifications, currentLoggedUser);
        return certifications;
    }

    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        userFacade.completeLecture(id);
    }

    @GetMapping(path = "/{username}/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentFullCourseDisplayDto getCourse(@PathVariable("id") Long id, @PathVariable("username") String username) {
        StudentFullCourseDisplayDto enrolledCourse = userFacade.getEnrolledCourse(username, id);
        enrolledCourse.add(linkTo(methodOn(MediaController.class).getFile(enrolledCourse.getName(), enrolledCourse.getCoverImageName())).withRel("cover-image"));
        addHateoasForLectures(enrolledCourse.getLectureWrapperDisplayDtos(), enrolledCourse.getName());
        return enrolledCourse;
    }

    private void addHateoasForCertifications(List<StudentCertificationDisplayDto> certifications, String username) {
        for (StudentCertificationDisplayDto certification : certifications) {
            certification.add(linkTo(methodOn(StudentController.class).getCourse(certification.getCourseId(), username)).withRel(LinkRelation.of("enrolled-course")));
        }
    }

    private void addHateoasForEnrolledCourses(List<StudentPreviewCourseDisplayDto> enrolledCourses, String name) {
        for (StudentPreviewCourseDisplayDto enrolledCourse : enrolledCourses) {
            enrolledCourse.add(linkTo(methodOn(StudentController.class).getCourse(enrolledCourse.getEnrolledCourseId(), name)).withRel(LinkRelation.of("enrolled-course")));
            enrolledCourse.add(linkTo(methodOn(MediaController.class).getFile(enrolledCourse.getName(), enrolledCourse.getCoverImageName())).withRel(LinkRelation.of("cover-image")));
        }
    }

    private void addHateoasForLectures(List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos, String folderName) {
        for (StudentFullLectureWrapperDisplayDto lectureWrapperDisplayDto : lectureWrapperDisplayDtos) {
            if (!lectureWrapperDisplayDto.getLectureFileNames().isEmpty()) {
                lectureWrapperDisplayDto.add(linkTo(methodOn(LectureController.class).zipLectureFiles(lectureWrapperDisplayDto.getLectureId())).withRel(LinkRelation.of("zip-lectures")));
                for (String lectureFileName : lectureWrapperDisplayDto.getLectureFileNames()) {
                    lectureWrapperDisplayDto.add(linkTo(methodOn(LectureController.class).downloadFile(lectureWrapperDisplayDto.getLectureId(), lectureFileName)).withRel(LinkRelation.of("download-file")));
                }
            }
            lectureWrapperDisplayDto.add(linkTo(methodOn(MediaController.class).getFile(folderName, lectureWrapperDisplayDto.getLectureVideoName())).withRel("video"));
        }
    }
}
