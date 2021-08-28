package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "This represents the object holding the comment of a student given to a course")
public class DisplayCommentDto {

    @Schema(description = "The comment id", example = "1")
    private Long id;

    @Schema(description = "The comment", example = "It is a very nice course!!!")
    private String description;

    @Schema(description = "The username of the student that gave the comment", example = "Andrei T.")
    private String username;

    @Schema(description = "The number of upvotes", example = "421")
    private Integer upVotes;

    @Schema(description = "The number of downvotes", example = "-15")
    private Integer downVotes;

    @Schema(description = "The date when the comment was given", example = "2021-05-15 15:23:09")
    private String timeStamp;
}
