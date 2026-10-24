package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface autorRepository extends JpaRepository<Autor,Long> {

    Page<Autor> findByNomeContainIgnoreCase (String nome , Pageable p);
}
