package com.ucv.codetech.facade;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.service.CategoryService;
import com.ucv.codetech.facade.converter.CategoryConverter;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CategoryFacade {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Transactional
    public Long create(CategoryDto categoryDto) {
        Category category = categoryConverter.dtoToEntity(categoryDto);
        return categoryService.saveOrUpdate(category).getId();
    }

    public CategoryDto find(Long id) {
        Category category = categoryService.findById(id);
        return categoryConverter.entityToDto(category);
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryService.findAll();
        return categoryConverter.entitiesToCategoryDtos(categories);
    }

    public void delete(Long id) {
        categoryService.deleteById(id);
    }

    public void edit(Long id, UpdateCategoryDto updateCategory) {
        Category category = categoryConverter.dtoToEntity(updateCategory);
        category.setId(id);
        categoryService.edit(category);
    }
}
