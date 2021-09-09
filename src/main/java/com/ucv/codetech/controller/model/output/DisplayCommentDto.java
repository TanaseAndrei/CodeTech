package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "This represents the object holding the comment of a student given to a course")
public class DisplayCommentDto {

    @ApiModelProperty(value = "The comment id", example = "1")
    private Long id;

    @ApiModelProperty(value = "The comment", example = "It is a very nice course!!!")
    private String description;

    @ApiModelProperty(value = "The username of the student that gave the comment", example = "Andrei T.")
    private String username;

    @ApiModelProperty(value = "The number of upvotes", example = "421")
    private Integer upVotes;

    @ApiModelProperty(value = "The number of downvotes", example = "-15")
    private Integer downVotes;

    @ApiModelProperty(value = "The date when the comment was given", example = "2021-05-15 15:23:09")
    private String timeStamp;
}
