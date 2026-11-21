package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.AutorRequest;
import com.example.ProjetoBiblioteca.dto.response.AutorResponse;
import com.example.ProjetoBiblioteca.model.Autor;
import com.example.ProjetoBiblioteca.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AutorService extends CrudService<Autor, Long, AutorRequest, AutorResponse> {

    public AutorService(AutorRepository repository) {
        super(repository);
    }

    @Override
    protected Autor toEntity(AutorRequest request) {
        Autor autor = new Autor();
        autor.setNome(request.nomeAutor());
        autor.setNacionalidade(request.nacionalidade());
        autor.setDataNascimento(request.dataNascimento());
        return autor;
    }

    @Override
    protected void updateEntity(Autor entity, AutorRequest request) {
        entity.setNome(request.nomeAutor());
        entity.setNacionalidade(request.nacionalidade());
        entity.setDataNascimento(request.dataNascimento());
    }

    @Override
    protected AutorResponse toResponse(Autor entity) {
        return new AutorResponse(
                entity.getId(),
                entity.getNome(),
                entity.getNacionalidade(),
                entity.getDataNascimento()
        );
    }
}

