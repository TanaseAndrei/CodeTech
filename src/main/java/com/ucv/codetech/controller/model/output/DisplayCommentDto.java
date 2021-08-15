package com.ucv.codetech.controller.model.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DisplayCommentDto {

    private Long id;

    private String description;

    private String username;

    private Integer upVotes;

    private Integer downVotes;

    private String timeStamp;
}
