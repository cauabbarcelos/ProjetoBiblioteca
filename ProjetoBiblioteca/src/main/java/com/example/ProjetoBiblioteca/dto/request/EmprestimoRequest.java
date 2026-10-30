package com.example.ProjetoBiblioteca.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequest(

        @NotNull(message = "O usuario é obrigatorio")
        Long usuarioId,

        @NotNull(message = "O livro é obrigatorio")
        Long livroId) {
}
