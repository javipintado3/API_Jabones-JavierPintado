package com.dwes.api.servicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dwes.api.entidades.Categoria;
import com.dwes.api.repositorios.CategoriaRepository;
import com.dwes.api.servicios.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriarepository;
	
	@Override
	public Page<Categoria> findAll(Pageable pageable) {
		return categoriarepository.findAll(pageable);
	}

	@Override
	public Optional<Categoria> findById(Long categoriaid) {
		return categoriarepository.findById(categoriaid);
	}

	@Override
	public Categoria save(Categoria categoria) {
		return categoriarepository.save(categoria);
	}

	@Override
	public void deleteById(Long categoriaid) {
		categoriarepository.deleteById(categoriaid);
		
	}

	@Override
	public boolean existsById(Long id) {
		return categoriarepository.existsById(id);
	}

}
