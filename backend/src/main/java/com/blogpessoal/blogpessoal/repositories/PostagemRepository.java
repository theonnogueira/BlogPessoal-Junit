package com.blogpessoal.blogpessoal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogpessoal.blogpessoal.models.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	@Query(nativeQuery = true, value="SELECT * FROM tb_postagens WHERE tb_postagens.titulo LIKE %:titulo%")
	List<Postagem> findPostagensByTitulo(String titulo);
		
}
