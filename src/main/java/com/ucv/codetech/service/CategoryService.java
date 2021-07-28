package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
import com.ucv.codetech.service.converter.CategoryConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepositoryGateway categoryRepositoryGateway;
    private final CategoryConverter categoryConverter;

    public Long createOrUpdate(CategoryDto categoryDto) {
        Category category = categoryConverter.categoryDtoToCategory(categoryDto);
        return categoryRepositoryGateway.saveOrUpdate(category);
    }

    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepositoryGateway.findAll();
        return categoryConverter.categoriesToCategoryDtos(categories);
    }

    public CategoryDto getById(Long id) {
        return categoryRepositoryGateway.findById(id).map(categoryConverter::categoryToCategoryDto)
                .orElseThrow(() -> new AppException("The specified category has not beed found!", HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id) {
        categoryRepositoryGateway.delete(id);
    }
}
