package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
public class CourseLectureDto {

    private String name;

    private String description;

    private MultipartFile lectureVideo;
}
