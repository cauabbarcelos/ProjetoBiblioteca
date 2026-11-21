package com.example.ProjetoBiblioteca.mapper;

import com.example.ProjetoBiblioteca.dto.request.UsuarioRequest;
import com.example.ProjetoBiblioteca.dto.response.UsuarioResponse;
import com.example.ProjetoBiblioteca.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioMapper {

    // UsuarioRequest -> Usuario novo
    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        updateEntity(usuario, request);
        usuario.setDataCadastro(LocalDate.now());   // só na criação
        return usuario;
    }

    // Copia os dados do request para um usuário que já existe (usado no PUT)
    public void updateEntity(Usuario usuario, UsuarioRequest request) {
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setTelefone(request.telefone());
    }

    // Usuario -> UsuarioResponse
    public UsuarioResponse toResponse(Usuario usuario) {
       return new UsuarioResponse(usuario.getId(),
        usuario.getEmail(),
        usuario.getNome(),
        usuario.getTelefone(),
        usuario.getDataCadastro());

    }
}
