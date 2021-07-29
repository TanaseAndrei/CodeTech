package com.ucv.codetech.repository;

import com.ucv.codetech.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryRepositoryGateway {

    private final CategoryRepository categoryRepository;

    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
