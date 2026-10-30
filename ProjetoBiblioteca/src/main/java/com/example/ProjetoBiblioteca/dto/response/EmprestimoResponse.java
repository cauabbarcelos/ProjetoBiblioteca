package com.example.ProjetoBiblioteca.dto.response;

import com.example.ProjetoBiblioteca.model.enums.StatusEmprestimo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmprestimoResponse(

        Long id,
        Long usuarioId,
        String usuarioNome,
        Long livroId,
        String livroTitulo,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao,
        StatusEmprestimo status,
        BigDecimal multa) {
}
