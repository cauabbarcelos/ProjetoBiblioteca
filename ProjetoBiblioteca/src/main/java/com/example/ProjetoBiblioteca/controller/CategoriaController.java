package com.example.ProjetoBiblioteca.controller;

import com.example.ProjetoBiblioteca.dto.request.CategoriaRequest;
import com.example.ProjetoBiblioteca.dto.response.CategoriaResponse;
import com.example.ProjetoBiblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // GET /categorias?nome=...&page=0&size=10&sort=nome,asc
    @GetMapping
    public Page<CategoriaResponse> listar(@RequestParam(required = false) String name ,
                                          Pageable pageable) {
        return service.listar(name , pageable);
    }

    @GetMapping("/{id}")
    public CategoriaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaResponse atualizar(@PathVariable Long id,
                                       @Valid @RequestBody CategoriaRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
