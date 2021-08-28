package com.ucv.codetech.controller.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "The lecture API")
public interface LectureApi {

    @ApiOperation(value = "Upload files to a lecture", httpMethod = "POST", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully added the files to a lecture"),
            @ApiResponse(code = 404, message = "The lecture does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void uploadLectureFiles(@Schema(description = "The id of the lecture", example = "1") Long lectureId,
                            @Schema(description = "The files to be uploaded") MultipartFile[] multipartFiles);

    @ApiOperation(value = "Delete a lecture", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted a lecture"),
            @ApiResponse(code = 404, message = "The lecture does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void deleteLecture(@Schema(description = "The id of the lecture", example = "1") Long id);

    @ApiOperation(value = "Delete a file from the lecture", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the file from a lecture"),
            @ApiResponse(code = 404, message = "The lecture does not exist"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void deleteLectureFile(@Schema(description = "The id of the lecture", example = "1") Long lectureId,
                           @Schema(description = "The name of the file to be deleted", example = "213vr-23r12-23vr12b-x.jpg") String fileName);
}
