package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.dto.request.CategoriaRequest;
import com.example.ProjetoBiblioteca.model.Autor;
import com.example.ProjetoBiblioteca.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    // Busca parcial pelo nome
    Page<Categoria> findByNomeContainingIgnoreCase(String name, Pageable pageable);

    // Busca exata pelo nome
    Optional<Categoria> findByNomeIgnoreCase(String name);

    // Para impedir "Romance" e "romance" como categorias diferentes
    boolean existsByNomeIgnoreCase(String name);
}
