package com.example.ProjetoBiblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record autorDto(

        Long id ,

        @NotBlank(message = "Nome é obrigatorio")
        @Size(max = 100 , message = "Nome no maximo 100 caracteres!")
        String nome ,

        @NotBlank(message = "Nascionalidade é obrigatoria ")
        @Size(max = 50 , message = "Nascionalidade no maximo 50 letras!")
        String nascionalida,

) {
}

