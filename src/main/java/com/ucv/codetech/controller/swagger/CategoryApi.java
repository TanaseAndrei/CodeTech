package com.ucv.codetech.controller.swagger;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.model.output.DisplayCategoryDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(value = "The category API")
public interface CategoryApi {

    Long createCategory(@Valid @RequestBody CategoryDto categoryDto);

    void editCategory(@PathVariable("id") Long id, @Valid @RequestBody UpdateCategoryDto updateCategory);

    List<DisplayCategoryDto> getAllCategories();

    DisplayCategoryDto getCategory(@PathVariable Long id);

    void deleteCategory(@PathVariable Long id);
}
