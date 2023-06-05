package com.pablovvoliveira.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pablovvoliveira.catalog.dto.CategoryDTO;
import com.pablovvoliveira.catalog.entities.Category;
import com.pablovvoliveira.catalog.repositories.CategoryRepository;
import com.pablovvoliveira.catalog.services.exceptions.DatabaseException;
import com.pablovvoliveira.catalog.services.exceptions.ResourceNotFoundException;

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
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
	    Optional<Category> optionalEntity = repository.findById(id);
	    if (optionalEntity.isPresent()) {
	        Category entity = optionalEntity.get();
	        entity.setName(dto.getName());
	        entity = repository.save(entity);
	        return new CategoryDTO(entity);
	    } else {
	        throw new ResourceNotFoundException("Id not found: " + id);
	    }
	}

	public void delete(Long id) {
	    try {
	        repository.deleteById(id);
	    } catch (EmptyResultDataAccessException e) {
	        throw new ResourceNotFoundException("Id not found: " + id);
	    } catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Integrity violation");
	    }
	}
	

}
