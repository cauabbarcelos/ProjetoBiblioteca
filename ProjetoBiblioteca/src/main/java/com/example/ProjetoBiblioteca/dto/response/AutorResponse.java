package com.example.ProjetoBiblioteca.dto.response;

import java.time.LocalDate;

public record  AutorResponse(
        Long id,
        String nome,
        String nacionalidade,
        LocalDate dataNascimento) {
}
