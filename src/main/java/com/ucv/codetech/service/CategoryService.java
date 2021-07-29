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

    public Category createOrUpdate(Category category) {
        return categoryRepositoryGateway.saveOrUpdate(category);
    }

    public List<Category> findAll() {
        return categoryRepositoryGateway.findAll();
    }

    public Category findById(Long id) {
        return categoryRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The specified category has not beed found!", HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id) {
        categoryRepositoryGateway.delete(id);
    }
}
