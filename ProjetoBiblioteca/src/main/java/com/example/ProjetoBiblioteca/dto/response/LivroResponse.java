package com.example.ProjetoBiblioteca.dto.response;

import java.util.List;

public record LivroResponse(

        Long id,
        String titulo,
        String isbn,
        Integer anoPublicacao,
        boolean disponivel,
        Long autorId,
        String autorNome,
        List<String> categorias
) {
}
