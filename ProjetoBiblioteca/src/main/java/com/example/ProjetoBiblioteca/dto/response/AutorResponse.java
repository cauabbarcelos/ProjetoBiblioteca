package com.example.ProjetoBiblioteca.dto.response;

import java.time.LocalDate;

public record  AutorResponse(
        Long id,
        String name,
        String nacionalidade,
        LocalDate dataNascimento) {
}
