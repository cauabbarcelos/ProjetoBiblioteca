package com.example.ProjetoBiblioteca.dto.response;

import java.time.LocalDate;

public record UsuarioResponse(

        Long id,
        String nome,
        String email,
        String telefone,
        LocalDate dataCadastro
){
}
