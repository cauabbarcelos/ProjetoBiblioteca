package com.example.ProjetoBiblioteca.mapper;

import com.example.ProjetoBiblioteca.dto.request.LivroRequest;
import com.example.ProjetoBiblioteca.dto.response.LivroResponse;
import com.example.ProjetoBiblioteca.model.Categoria;
import com.example.ProjetoBiblioteca.model.Livro;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {

    // LivroRequest -> Livro novo
    public Livro toEntity(LivroRequest request) {
        Livro livro = new Livro();
        updateEntity(livro, request);
        livro.setDisponivel(true);   // todo livro novo nasce disponível
        return livro;
    }

    // Copia os campos simples do request para um livro existente (usado no PUT)
    public void updateEntity(Livro livro, LivroRequest request) {
        livro.setTitulo(request.tituloLivro());
        livro.setIsbn(request.isbnLivro());
        livro.setAnoPublicacao(request.anoPublicacao());
    }

    // Livro -> LivroResponse
    public LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.isDisponivel(),
                livro.getAutor().getId(),
                livro.getAutor().getNome(),
                livro.getCategorias().stream()
                        .map(Categoria::getNome)
                        .sorted()
                        .toList());
    }
}
