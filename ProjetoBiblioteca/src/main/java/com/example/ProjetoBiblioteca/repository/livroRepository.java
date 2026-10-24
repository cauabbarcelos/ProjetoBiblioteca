package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface livroRepository extends JpaRepository<Livro,Long> {
}
