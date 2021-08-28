package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.model.output.DisplayCategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

import java.util.List;

@Api(value = "The category API")
public interface CategoryApi {

    @ApiOperation(value = "Create a category", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create a category"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    Long createCategory(CategoryDto categoryDto);

    @ApiOperation(value = "Edit a category", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated a category"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void editCategory(@Schema(description = "The id of the comment", example = "1") Long id, UpdateCategoryDto updateCategory);

    @ApiOperation(value = "Get all categories", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a category"),
            @ApiResponse(code = 403, message = "The user must be logged in")
    })
    List<DisplayCategoryDto> getAllCategories();

//    @ApiOperation(value = "Get all categories", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successfully updated a category"),
//            @ApiResponse(code = 403, message = "The user must be logged in")
//    })
//    DisplayCategoryDto getCategory(@Schema(description = "The id of the comment", example = "1") Long id);

    @ApiOperation(value = "Delete a category", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated a category"),
            @ApiResponse(code = 403, message = "The instructor must be logged in")
    })
    void deleteCategory(@Schema(description = "The id of the comment", example = "1") Long id);
}
