package com.blogpessoal.blogpessoal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogpessoal.blogpessoal.models.Postagem;
import com.blogpessoal.blogpessoal.models.Tema;
import com.blogpessoal.blogpessoal.repositories.PostagemRepository;
import com.blogpessoal.blogpessoal.repositories.TemaRepository;
import com.blogpessoal.blogpessoal.services.exceptions.DatabaseException;
import com.blogpessoal.blogpessoal.services.exceptions.ResourceNotFoundException;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository repository;

	@Autowired
	private TemaRepository temaRepository;

	@Transactional(readOnly = true)
	public List<Postagem> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public Postagem findById(Long id) {
		Optional<Postagem> obj = repository.findById(id);

		Postagem entity = obj.orElseThrow(() -> new ResourceNotFoundException("Postagem não encontrada!"));

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Postagem> findByTitulo(String titulo) {
		List<Postagem> list = repository.findPostagensByTitulo(titulo);

		return list;
	}

	@Transactional
	public Postagem insert(Postagem entity) {
		Optional<Tema> obj = temaRepository.findById(entity.getTema().getId());
		obj.orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado"));
		
		entity = repository.save(entity);

		return entity;
	}

	@Transactional
	public Postagem update(Postagem entity) {

		Optional<Postagem> obj = repository.findById(entity.getId());
		Postagem postagem = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
		postagem = repository.save(entity);

		return postagem;

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
