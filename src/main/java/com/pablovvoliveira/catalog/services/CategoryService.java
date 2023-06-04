package com.pablovvoliveira.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablovvoliveira.catalog.dto.CategoryDTO;
import com.pablovvoliveira.catalog.entities.Category;
import com.pablovvoliveira.catalog.repositories.CategoryRepository;
import com.pablovvoliveira.catalog.services.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();		
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}
	
}
