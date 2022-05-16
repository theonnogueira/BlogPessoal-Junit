package com.blogpessoal.blogpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogpessoal.blogpessoal.models.Tema;
import com.blogpessoal.blogpessoal.repositories.TemaRepository;
import com.blogpessoal.blogpessoal.services.exceptions.DatabaseException;
import com.blogpessoal.blogpessoal.services.exceptions.ResourceNotFoundException;

@Service
public class TemaService {

	@Autowired
	private TemaRepository repository;

	@Transactional(readOnly = true)
	public List<Tema> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public Tema getById(Long id) {
		Optional<Tema> obj = repository.findById(id);

		Tema entity = obj.orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado!"));

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Tema> getByDescricao(String descricao) {
		List<Tema> list = repository.findAllByDescricaoContainingIgnoreCase(descricao);

		return list;
	}

	@Transactional
	public Tema insert(Tema tema) {
		tema = repository.save(tema);

		return tema;
	}

	@Transactional
	public Tema update(Tema entity) {
		Optional<Tema> obj = repository.findById(entity.getId());
		
		Tema tema = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
		tema = repository.save(tema);
		
		return tema;
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}
}
