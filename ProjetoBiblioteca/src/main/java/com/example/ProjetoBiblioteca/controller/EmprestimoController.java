package com.example.ProjetoBiblioteca.controller;

import com.example.ProjetoBiblioteca.dto.request.EmprestimoRequest;
import com.example.ProjetoBiblioteca.dto.response.EmprestimoResponse;
import com.example.ProjetoBiblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService service;

    // Registra um empréstimo (valida limite de 3 e disponibilidade do livro)
    @PostMapping
    public ResponseEntity<EmprestimoResponse> registrar(@Valid @RequestBody EmprestimoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    // Registra a devolução e calcula a multa
    @PutMapping("/{id}/devolucao")
    public EmprestimoResponse devolver(@PathVariable Long id) {
        return service.devolver(id);
    }

    // GET /emprestimos?usuarioId=1&page=0&size=10
    @GetMapping
    public Page<EmprestimoResponse> listar(@RequestParam(required = false) Long usuarioId,
                                           Pageable pageable) {
        return service.listar(usuarioId, pageable);
    }

    @GetMapping("/atrasados")
    public List<EmprestimoResponse> listarAtrasados() {
        return service.listarAtrasados();
    }

    @GetMapping("/{id}")
    public EmprestimoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
