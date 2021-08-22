package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullLectureWrapperDisplayDto;
import com.ucv.codetech.controller.model.output.StudentPreviewCourseDisplayDto;
import com.ucv.codetech.controller.swagger.StudentApi;
import com.ucv.codetech.facade.AuthenticationFacade;
import com.ucv.codetech.service.UrlService;
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
    private final UrlService urlService;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentPreviewCourseDisplayDto> getCourses() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<StudentPreviewCourseDisplayDto> enrolledCourses = userFacade.getEnrolledCourses(currentLoggedUser);
        addHateoasForEnrolledCourses(enrolledCourses);
        return enrolledCourses;
    }

    @GetMapping(path = "/certifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCertificationDisplayDto> getCertifications() {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        List<StudentCertificationDisplayDto> certifications = userFacade.getCertifications(currentLoggedUser);
        addHateoasForCertifications(certifications);
        return certifications;
    }

    @PatchMapping(path = "/lecture/{id}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeLecture(@PathVariable("id") Long id) {
        userFacade.completeLecture(id);
    }

    @GetMapping(path = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentFullCourseDisplayDto getCourse(@PathVariable("id") Long id) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        StudentFullCourseDisplayDto enrolledCourse = userFacade.getEnrolledCourse(currentLoggedUser, id);
        enrolledCourse.add(urlService.getLinkForGettingMedia(enrolledCourse.getName(), enrolledCourse.getCoverImageName(), "cover-image"));
        addHateoasForLectures(enrolledCourse.getLectureWrapperDisplayDtos(), enrolledCourse.getName());
        return enrolledCourse;
    }

    private void addHateoasForCertifications(List<StudentCertificationDisplayDto> certifications) {
        for (StudentCertificationDisplayDto certification : certifications) {
            certification.add(linkTo(methodOn(StudentController.class).getCourse(certification.getCourseId())).withRel(LinkRelation.of("enrolled-course")));
        }
    }

    private void addHateoasForEnrolledCourses(List<StudentPreviewCourseDisplayDto> enrolledCourses) {
        for (StudentPreviewCourseDisplayDto enrolledCourse : enrolledCourses) {
            enrolledCourse.add(linkTo(methodOn(StudentController.class).getCourse(enrolledCourse.getEnrolledCourseId())).withRel(LinkRelation.of("enrolled-course")));
            enrolledCourse.add(urlService.getLinkForGettingMedia(enrolledCourse.getName(), enrolledCourse.getCoverImageName(), "cover-image"));
        }
    }

    private void addHateoasForLectures(List<StudentFullLectureWrapperDisplayDto> lectureWrapperDisplayDtos, String folderName) {
        for (StudentFullLectureWrapperDisplayDto lectureWrapperDisplayDto : lectureWrapperDisplayDtos) {
            if (!lectureWrapperDisplayDto.getLectureFileNames().isEmpty()) {
                lectureWrapperDisplayDto.add(urlService.getLinkForZippingFiles(folderName));
                for (String lectureFileName : lectureWrapperDisplayDto.getLectureFileNames()) {
                    lectureWrapperDisplayDto.add(urlService.getLinkForGettingMedia(folderName, lectureFileName, "download-file"));
                }
            }
            lectureWrapperDisplayDto.add(urlService.getLinkForGettingMedia(folderName, lectureWrapperDisplayDto.getLectureVideoName(), "video"));
        }
    }
}
