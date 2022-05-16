package com.blogpessoal.blogpessoal.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blogpessoal.blogpessoal.models.Tema;
import com.blogpessoal.blogpessoal.services.TemaService;

@RestController
@RequestMapping(value = "/temas")
public class TemaController {

	@Autowired
	TemaService service;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.getById(id));
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok().body(service.getByDescricao(descricao));
	}

	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema) {
		tema = service.insert(tema);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tema.getId()).toUri();
		return ResponseEntity.created(uri).body(tema);
	}

	@PutMapping
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema) {
		return ResponseEntity.ok().body(service.update(tema));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
