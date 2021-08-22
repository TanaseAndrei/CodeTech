package com.ucv.codetech.facade;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.service.CategoryService;
import com.ucv.codetech.facade.converter.CategoryConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
@Slf4j
public class CategoryFacade {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Transactional
    public Long create(CategoryDto categoryDto) {
        log.info("Creating new category with name {}", categoryDto.getName());
        Category category = categoryConverter.dtoToEntity(categoryDto);
        return categoryService.saveOrUpdate(category).getId();
    }

    public CategoryDto find(Long id) {
        log.info("Searching category with id {}", id);
        Category category = categoryService.findById(id);
        log.info("Found category with name {}", category.getName());
        return categoryConverter.entityToDto(category);
    }

    public List<CategoryDto> findAll() {
        log.info("Retrieving all categories");
        List<Category> categories = categoryService.findAll();
        return categoryConverter.entitiesToCategoryDtos(categories);
    }

    public void delete(Long id) {
        categoryService.deleteById(id);
        log.info("Delete category with id {}", id);
    }

    public void edit(Long id, UpdateCategoryDto updateCategory) {
        Category category = categoryConverter.dtoToEntity(updateCategory);
        category.setId(id);
        categoryService.edit(category);
        log.info("Edited category with id {}", id);
    }
}
