package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "The lecture dto used to create a lecture")
public class LectureDto {

    @NotBlank(message = "The name of the lecture should not be empty")
    @ApiModelProperty(required = true, value = "The name of the lecture", example = "Polymorphism")
    private String name;

    @NotBlank(message = "The description of the lecture should not be empty")
    @ApiModelProperty(required = true, value = "The description of the lecture")
    private String description;

    @NotNull(message = "The lecture must have a video")
    @ApiModelProperty(required = true, value = "The video of the lecture")
    private MultipartFile lectureVideo;
}
