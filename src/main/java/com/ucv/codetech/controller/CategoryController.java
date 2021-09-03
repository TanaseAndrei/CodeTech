package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.CategoryDto;
import com.ucv.codetech.controller.model.input.UpdateCategoryDto;
import com.ucv.codetech.controller.model.output.DisplayCategoryDto;
import com.ucv.codetech.controller.swagger.CategoryApi;
import com.ucv.codetech.facade.CategoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryFacade categoryFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryFacade.create(categoryDto);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editCategory(@PathVariable("id") Long id, @Valid @RequestBody UpdateCategoryDto updateCategory) {
        categoryFacade.update(id, updateCategory);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DisplayCategoryDto> getAllCategories() {
        return categoryFacade.findAll();
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryFacade.delete(id);
    }
}
