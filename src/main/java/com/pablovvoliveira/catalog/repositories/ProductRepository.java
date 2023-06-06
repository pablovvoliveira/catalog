package com.pablovvoliveira.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablovvoliveira.catalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
