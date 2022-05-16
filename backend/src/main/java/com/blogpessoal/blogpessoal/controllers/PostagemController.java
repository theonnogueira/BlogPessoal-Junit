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

import com.blogpessoal.blogpessoal.models.Postagem;
import com.blogpessoal.blogpessoal.services.PostagemService;

@RestController
@RequestMapping(value = "/postagens")
public class PostagemController {

	@Autowired
	PostagemService service;

	@GetMapping
	public ResponseEntity<List<Postagem>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> findByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok().body(service.findByTitulo(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> insert(@Valid @RequestBody Postagem postagem) {
		postagem = service.insert(postagem);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(postagem.getId())
				.toUri();

		return ResponseEntity.created(uri).body(postagem);
	}

	@PutMapping
	public ResponseEntity<Postagem> update(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.ok().body(service.update(postagem));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
