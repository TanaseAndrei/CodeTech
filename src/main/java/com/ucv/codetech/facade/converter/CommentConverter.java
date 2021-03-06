package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.CommentDto;
import com.ucv.codetech.controller.model.output.DisplayCommentDto;
import com.ucv.codetech.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {

    public Comment dtoToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setTimeStamp();
        comment.setDescription(commentDto.getDescription());
        return comment;
    }

    public List<DisplayCommentDto> entitiesToDisplayCommentDtos(List<Comment> comments) {
        return comments
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public DisplayCommentDto entityToDto(Comment comment) {
        DisplayCommentDto displayCommentDto = new DisplayCommentDto();
        displayCommentDto.setId(comment.getId());
        displayCommentDto.setUsername(comment.getStudent().getUsername());
        displayCommentDto.setDescription(comment.getDescription());
        displayCommentDto.setTimeStamp(comment.getCommentDate().toString());
        displayCommentDto.setDownVotes(comment.getDownVotes());
        displayCommentDto.setUpVotes(comment.getUpVotes());
        return displayCommentDto;
    }
}
