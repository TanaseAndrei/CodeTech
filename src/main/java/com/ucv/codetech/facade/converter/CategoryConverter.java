package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.model.output.DisplayCategoryDto;
import com.ucv.codetech.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    public Category dtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public List<DisplayCategoryDto> entitiesToDisplayCategoryDtos(List<Category> categories) {
        return categories
                .stream()
                .map(this::entityToDisplayCategoryDto)
                .collect(Collectors.toList());
    }

    public DisplayCategoryDto entityToDisplayCategoryDto(Category category) {
        DisplayCategoryDto displayCategoryDto = new DisplayCategoryDto();
        displayCategoryDto.setName(category.getName());
        displayCategoryDto.setId(category.getId());
        return displayCategoryDto;
    }

    public Category dtoToEntity(UpdateCategoryDto updateCategoryDto) {
        Category category = new Category();
        category.setName(updateCategoryDto.getName());
        return category;
    }
}
