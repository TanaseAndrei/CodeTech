package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class LectureDto {

    @NotBlank(message = "The name of the lecture should not be empty")
    private String name;

    @NotBlank(message = "The description of the lecture should not be empty")
    private String description;

    @NotNull(message = "The lecture must have a video")
    private MultipartFile lectureVideo;
}
