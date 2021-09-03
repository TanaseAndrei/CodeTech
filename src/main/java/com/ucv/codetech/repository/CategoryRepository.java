package com.ucv.codetech.repository;

import com.ucv.codetech.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    boolean existsById(Long id);
}
