package com.example.ProjetoBiblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(

        @NotBlank(message = "Preecisa Informar qual o nome do livro")
        @Size(max = 50 , message = "digite no maximo 50 caracteres")
        String nameLivro,

        @Size(max = 250 , message = "Descricao no maximo 250 caracteres ")
        String descrica)
   {
}
