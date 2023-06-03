package com.pablovvoliveira.catalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablovvoliveira.catalog.entities.Category;
import com.pablovvoliveira.catalog.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional
	public List<Category> findAll() {
		return repository.findAll();
	}
	
}
