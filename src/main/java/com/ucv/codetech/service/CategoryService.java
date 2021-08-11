package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Category;
import com.ucv.codetech.repository.CategoryRepositoryGateway;
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

    public Category saveOrUpdate(Category category) {
        if(categoryRepositoryGateway.existsByName(category.getName())) {
            throw new AppException("The category already exists with this name", HttpStatus.BAD_REQUEST);
        }
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
        if(!categoryRepositoryGateway.existsById(id)) {
            throw new AppException("The category with id " + id + " does not exist", HttpStatus.BAD_REQUEST);
        }
        categoryRepositoryGateway.delete(id);
    }

    public void edit(Long id, String name) {
        Category category = findById(id);
        category.setName(name);
        categoryRepositoryGateway.saveOrUpdate(category);
    }
}
