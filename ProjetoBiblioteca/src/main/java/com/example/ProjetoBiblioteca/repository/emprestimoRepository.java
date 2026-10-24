package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface emprestimoRepository extends JpaRepository<Emprestimo,Long> {
}
