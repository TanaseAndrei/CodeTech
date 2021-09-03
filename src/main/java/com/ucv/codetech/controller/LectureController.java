package com.ucv.codetech.controller;

import com.ucv.codetech.controller.swagger.LectureApi;
import com.ucv.codetech.facade.LectureFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lectures")
@AllArgsConstructor
public class LectureController implements LectureApi {

    private final LectureFacade lectureFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(value = "/{id}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadLectureFiles(@PathVariable("id") Long lectureId, @RequestParam("files") MultipartFile[] multipartFiles) {
        lectureFacade.uploadFiles(lectureId, multipartFiles);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLecture(@PathVariable("id") Long id) {
        lectureFacade.delete(id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}/file/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLectureFile(@PathVariable("id") Long lectureId, @PathVariable("fileName") String fileName) {
        lectureFacade.deleteFile(lectureId, fileName);
    }
}
