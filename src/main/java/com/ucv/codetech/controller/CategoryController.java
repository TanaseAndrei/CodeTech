package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCategory(@RequestBody CategoryDto categoryDto) {
        Long id = categoryService.createOrUpdate(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
