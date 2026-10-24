package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class CrudService<T, ID, REQ, RES> {

    protected final JpaRepository<T, ID> repository;

    protected CrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected abstract T toEntity(REQ request);
    protected abstract void updateEntity(T entity, REQ request);
    protected abstract RES toResponse(T entity);

    @Transactional
    public RES criar(REQ request) {
        return toResponse(repository.save(toEntity(request)));
    }

    @Transactional(readOnly = true)
    public Page<RES> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public RES buscarPorId(ID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public RES atualizar(ID id, REQ request) {
        T entity = buscarEntidade(id);
        updateEntity(entity, request);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void deletar(ID id) {
        repository.delete(buscarEntidade(id));
    }

    protected T buscarEntidade(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro não encontrado: " + id));
    }
}