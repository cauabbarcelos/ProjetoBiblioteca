package com.example.ProjetoBiblioteca.controller;

import com.example.ProjetoBiblioteca.dto.request.LivroRequest;
import com.example.ProjetoBiblioteca.dto.response.LivroResponse;
import com.example.ProjetoBiblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<LivroResponse> criar(@Valid @RequestBody LivroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // GET /livros?titulo=dom   |   ?autorId=1   |   ?categoriaId=2   (um filtro por vez)
    @GetMapping
    public Page<LivroResponse> listar(@RequestParam(required = false) String titulo,
                                      @RequestParam(required = false) Long autorId,
                                      @RequestParam(required = false) Long categoriaId,
                                      Pageable pageable) {
        return service.listar(titulo, autorId, categoriaId, pageable);
    }

    @GetMapping("/{id}")
    public LivroResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public LivroResponse atualizar(@PathVariable Long id,
                                   @Valid @RequestBody LivroRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
