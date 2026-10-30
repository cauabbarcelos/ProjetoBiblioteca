package com.example.ProjetoBiblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "Nome é obrigatoro")
        @Size(max = 50 , message = "nome deve conter no maximo 50 caracteres")
        String nome ,

        @NotBlank(message = "Email obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos")
        String telefone)
    {
}
