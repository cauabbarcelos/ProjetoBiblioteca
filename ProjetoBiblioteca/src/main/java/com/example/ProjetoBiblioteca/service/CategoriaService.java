package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.CategoriaRequest;
import com.example.ProjetoBiblioteca.dto.response.CategoriaResponse;
import com.example.ProjetoBiblioteca.model.Categoria;
import com.example.ProjetoBiblioteca.model.Livro;
import com.example.ProjetoBiblioteca.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends CrudService<Categoria,Long, CategoriaRequest , CategoriaResponse> {


}