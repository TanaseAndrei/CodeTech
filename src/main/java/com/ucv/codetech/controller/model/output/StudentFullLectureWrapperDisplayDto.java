package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Relation(collectionRelation = "lectures")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "The dto used to display a full lecture of a student")
public class StudentFullLectureWrapperDisplayDto extends RepresentationModel<StudentFullLectureWrapperDisplayDto> {

    @ApiModelProperty(value = "The wrapper lecture id of the student", example = "1")
    private Long id;

    @ApiModelProperty(value = "The lecture id", example = "1")
    private Long lectureId;

    @ApiModelProperty(value = "The name of the lecture", example = "Java Polymorphism")
    private String name;

    @ApiModelProperty(value = "The flag representing the completion of the lecture", example = "true")
    private boolean completedLecture;

    @ApiModelProperty(value = "The video name of the lecture", example = "123412c34-3cv23rv23-213b412-2134123.mp4")
    private String lectureVideoName;

    @ApiModelProperty(value = "The description of the lecture")
    private String description;

    @ApiModelProperty(value = "The lecture files", example = "1")
    private List<String> lectureFileNames = new ArrayList<>();
}
