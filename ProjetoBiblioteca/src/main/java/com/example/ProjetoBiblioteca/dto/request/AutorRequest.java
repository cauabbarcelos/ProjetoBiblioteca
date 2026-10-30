package com.example.ProjetoBiblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AutorRequest(

        @NotBlank(message = "Nome obrigatorio")
        String nomeAutor,
        String nacionalidade,

        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate dataNascimento) {
}
