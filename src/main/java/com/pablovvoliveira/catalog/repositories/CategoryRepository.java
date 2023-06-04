package com.pablovvoliveira.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablovvoliveira.catalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
