package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.model.output.DisplayCategoryDto;
import com.ucv.codetech.facade.converter.CategoryConverter;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@AllArgsConstructor
@Slf4j
public class CategoryFacade {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Transactional
    public Long create(CategoryDto categoryDto) {
        log.info("Creating new category with the name {}", categoryDto.getName());
        Category category = categoryConverter.dtoToEntity(categoryDto);
        Long id = categoryService.saveOrUpdate(category).getId();
        log.info("Created a new category with the name {}", categoryDto.getName());
        return id;
    }

    public List<DisplayCategoryDto> findAll() {
        log.info("Retrieving all categories");
        List<Category> categories = categoryService.findAll();
        return categoryConverter.entitiesToDisplayCategoryDtos(categories);
    }

    public void delete(Long id) {
        log.info("Deleting the category with the id {}", id);
        categoryService.deleteById(id);
        log.info("Deleted the category with the id {}", id);
    }

    public void update(Long id, UpdateCategoryDto updateCategory) {
        log.info("Updating the category with the id {}", id);
        Category category = categoryConverter.dtoToEntity(updateCategory);
        category.setId(id);
        categoryService.edit(category);
        log.info("Updated category with id {}", id);
    }
}
