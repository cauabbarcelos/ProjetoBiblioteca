package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca parcial pelo nome, sem diferenciar maiúsculas de minúsculas

    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Para impedir dois usuários com o mesmo e-mail

    boolean existsByEmail(String email);

    // Para o login (nos extras, com Spring Security)
    Optional<Usuario> findByEmail(String email);
}