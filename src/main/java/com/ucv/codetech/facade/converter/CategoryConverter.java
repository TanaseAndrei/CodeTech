package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.CategoryDto;
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

    public List<CategoryDto> categoriesToCategoryDtos(List<Category> categories) {
        return categories
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto entityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }
}
