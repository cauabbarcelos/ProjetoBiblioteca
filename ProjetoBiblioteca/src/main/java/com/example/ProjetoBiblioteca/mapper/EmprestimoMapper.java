package com.example.ProjetoBiblioteca.mapper;

import com.example.ProjetoBiblioteca.dto.response.EmprestimoResponse;
import com.example.ProjetoBiblioteca.model.Emprestimo;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoMapper {

    public EmprestimoResponse toResponse(Emprestimo emprestimo) {
        return new EmprestimoResponse(

                emprestimo.getId(),
                emprestimo.getUsuario().getId(),
                emprestimo.getUsuario().getNome(),
                emprestimo.getLivro().getId(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getMulta());
    }
}