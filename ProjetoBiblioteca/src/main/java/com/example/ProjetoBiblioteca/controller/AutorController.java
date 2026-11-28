package com.example.ProjetoBiblioteca.controller;

import com.example.ProjetoBiblioteca.dto.request.AutorRequest;
import com.example.ProjetoBiblioteca.dto.response.AutorResponse;
import com.example.ProjetoBiblioteca.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<AutorResponse> criar(@Valid @RequestBody AutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // GET /autores?nome=...&page=0&size=10&sort=nome,asc
    @GetMapping
    public Page<AutorResponse> listar(@RequestParam(required = false) String nome,
                                      Pageable pageable) {
        return service.listar(nome, pageable);
    }

    @GetMapping("/{id}")
    public AutorResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AutorResponse atualizar(@PathVariable Long id,
                                   @Valid @RequestBody AutorRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
