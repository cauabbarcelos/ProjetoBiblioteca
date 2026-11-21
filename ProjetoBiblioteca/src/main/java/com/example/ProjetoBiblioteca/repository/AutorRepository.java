package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.dto.response.AutorResponse;
import com.example.ProjetoBiblioteca.model.Autor;
import com.example.ProjetoBiblioteca.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    Page<Autor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
