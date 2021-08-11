package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "The category name should not be empty")
    private String name;
}
