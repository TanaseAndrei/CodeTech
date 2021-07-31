package com.ucv.codetech.facade;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.service.CategoryService;
import com.ucv.codetech.facade.converter.CategoryConverter;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CategoryFacade {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    public Long createOrUpdate(CategoryDto categoryDto) {
        Category category = categoryConverter.categoryDtoToCategory(categoryDto);
        return categoryService.createOrUpdate(category).getId();
    }

    public CategoryDto findById(Long id) {
        Category category = categoryService.findById(id);
        return categoryConverter.categoryToCategoryDto(category);
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryService.findAll();
        return categoryConverter.categoriesToCategoryDtos(categories);
    }

    public void deleteById(Long id) {
        categoryService.deleteById(id);
    }
}
