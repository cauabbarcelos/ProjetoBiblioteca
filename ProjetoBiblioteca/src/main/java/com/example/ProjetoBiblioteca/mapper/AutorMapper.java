package com.example.ProjetoBiblioteca.mapper;

import com.example.ProjetoBiblioteca.dto.response.AutorResponse;
import com.example.ProjetoBiblioteca.model.Autor;

public class AutorMapper {

    public AutorResponse toResponse(Autor autor) {

        return new AutorResponse(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getDataNascimento());
    }
}
