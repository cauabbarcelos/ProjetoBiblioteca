package com.example.ProjetoBiblioteca.mapper;

import com.example.ProjetoBiblioteca.dto.request.CategoriaRequest;
import com.example.ProjetoBiblioteca.dto.response.CategoriaResponse;
import com.example.ProjetoBiblioteca.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {


    // CategoriaRequest -> Categoria (para salvar um registro novo)
    public Categoria toEntity(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        updateEntity(categoria, request);
        return categoria;
    }

    // Copia os dados do request para uma categoria que já existe (usado no PUT)
    public void updateEntity(Categoria categoria, CategoriaRequest request) {
        categoria.setNome(request.nameLivro());
        categoria.setDescricao(request.descricao());
    }


    // Categoria -> CategoriaResponse (para devolver ao cliente)

    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }
}
