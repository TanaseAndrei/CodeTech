package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import com.ucv.codetech.controller.model.output.DisplayCommentDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "The comment API")
public interface CommentApi {

    DisplayCommentDto getComment(@PathVariable("id") Long id);

    void edit(@PathVariable("id") Long id, @Valid @RequestBody UpdateCommentDto updateComment);

    void upVote(@PathVariable("id") Long id);

    void downVote(@PathVariable("id") Long id);

    void delete(@PathVariable("id") Long id);
}
