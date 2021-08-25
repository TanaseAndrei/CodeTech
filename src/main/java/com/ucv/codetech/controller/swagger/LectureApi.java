package com.ucv.codetech.controller.swagger;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "The lecture API")
public interface LectureApi {

    void uploadLectureFiles(@PathVariable("id") Long lectureId, @RequestParam("files") MultipartFile[] multipartFiles);

    void deleteLecture(@PathVariable("id") Long id);

    void deleteLectureFile(@PathVariable("id") Long lectureId, @PathVariable("fileName") String fileName);
}
