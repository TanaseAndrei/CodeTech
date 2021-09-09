package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "The dto used to display the certification of a student")
public class StudentCertificationDisplayDto extends RepresentationModel<StudentCertificationDisplayDto> {

    @ApiModelProperty(value = "The name of the student", example = "Tanase A. Cristian")
    private String studentName;

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String courseName;

    @ApiModelProperty(value = "The date when the student got the certification", example = "2021-07-08 15:03:24")
    private String certificationDate;

    @JsonIgnore
    private Long courseId;
}
