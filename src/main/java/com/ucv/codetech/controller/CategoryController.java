package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.facade.CategoryFacade;
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

    private final CategoryFacade categoryFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryFacade.createOrUpdate(categoryDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategories() {
        return categoryFacade.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryFacade.findById(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.deleteById(id);
    }
}
