package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<Usuario,Long> {


}
