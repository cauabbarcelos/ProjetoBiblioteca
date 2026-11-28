package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.CategoriaRequest;
import com.example.ProjetoBiblioteca.dto.response.CategoriaResponse;
import com.example.ProjetoBiblioteca.exception.RegraNegocioException;
import com.example.ProjetoBiblioteca.mapper.CategoriaMapper;
import com.example.ProjetoBiblioteca.model.Categoria;
import com.example.ProjetoBiblioteca.repository.CategoriaRepository;
import com.example.ProjetoBiblioteca.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

@Service
public class CategoriaService extends CrudService<
        Categoria,
        Long,
        CategoriaRequest,
        CategoriaResponse> {

     CategoriaService(JpaRepository<Categoria, Long> repository) {
        super(repository);

        final CategoriaRepository categoriaRepository;
        final LivroRepository livroRepository;
        final CategoriaMapper mapper;

    }

    @Override
    protected Categoria toEntity(CategoriaRequest request) {
        return toEntity(request);
    }

    @Override
    protected void updateEntity(Categoria categoria, CategoriaRequest request) {
       updateEntity(categoria, request);
    }

    @Override
    protected CategoriaResponse toResponse(Categoria categoria) {
        return toResponse(categoria);
    }

}