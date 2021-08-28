package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

@Api(value = "The comment API")
public interface CommentApi {
//
//    DisplayCommentDto getComment(Long id);

    @ApiOperation(value = "Edit a comment", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated a comment"),
            @ApiResponse(code = 404, message = "The comment does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void edit(@Schema(description = "The id of the comment", example = "1") Long id, UpdateCommentDto updateComment);

    @ApiOperation(value = "Upvote a comment", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully upvoted a comment"),
            @ApiResponse(code = 404, message = "The comment does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void upVote(@Schema(description = "The id of the comment", example = "1") Long id);

    @ApiOperation(value = "Downvote a comment", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully downvoted a comment"),
            @ApiResponse(code = 404, message = "The comment does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void downVote(@Schema(description = "The id of the comment", example = "1") Long id);

    @ApiOperation(value = "Delete a comment", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted a comment"),
            @ApiResponse(code = 404, message = "The comment does not exist"),
            @ApiResponse(code = 403, message = "The student must be logged in")
    })
    void delete(@Schema(description = "The id of the comment", example = "1") Long id);
}
