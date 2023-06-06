package com.pablovvoliveira.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pablovvoliveira.catalog.dto.ProductDTO;
import com.pablovvoliveira.catalog.entities.Product;
import com.pablovvoliveira.catalog.repositories.ProductRepository;
import com.pablovvoliveira.catalog.services.exceptions.DatabaseException;
import com.pablovvoliveira.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);		
		return list.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
	    Optional<Product> optionalEntity = repository.findById(id);
	    if (optionalEntity.isPresent()) {
	        Product entity = optionalEntity.get();
	        //entity.setName(dto.getName());
	        entity = repository.save(entity);
	        return new ProductDTO(entity);
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
