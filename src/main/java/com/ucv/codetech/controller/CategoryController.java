package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.swagger.CategoryApi;
import com.ucv.codetech.facade.CategoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryFacade categoryFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public URI createCategory(@RequestBody CategoryDto categoryDto) {
        Long categoryId = categoryFacade.create(categoryDto);
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryId).toUri();
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editCategory(@PathVariable("id") Long id, @RequestBody UpdateCategoryDto updateCategory) {
        categoryFacade.edit(id, updateCategory);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> getAllCategories() {
        return categoryFacade.findAll();
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryFacade.findById(id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.deleteById(id);
    }
}
