package com.example.ProjetoBiblioteca.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record  LivroRequest (

        @NotBlank(message = "Titulo é obrigatorio")
        @Size(max = 150 , message = "O Titulo do livro deve ter no maximo 150 caracteres")
        String nomeLivro,

        @Size(max = 50 , message = "O isbn deve ter no maximo 50 caracteres")
        String isbnLivro,

        @Min(value = 1000, message = "Ano de publicação inválido")
        @Max(value = 2100, message = "Ano de publicação inválido")
        Integer anoPublicacao,

        @NotNull(message = "O autor e obrigatorio")
        Long autorId,

        @NotEmpty(message = "Mencione ao menos uma categoria")
        Set<Long> categoriaIds)
   {
}
