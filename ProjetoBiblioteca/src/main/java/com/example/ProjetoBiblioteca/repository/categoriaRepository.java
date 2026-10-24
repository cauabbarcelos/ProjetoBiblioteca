package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoriaRepository extends JpaRepository<Categoria ,Long> {
}
