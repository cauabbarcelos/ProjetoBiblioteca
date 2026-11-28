package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.LivroRequest;
import com.example.ProjetoBiblioteca.dto.response.LivroResponse;
import com.example.ProjetoBiblioteca.exception.RecursoNaoEncontradoException;
import com.example.ProjetoBiblioteca.exception.RegraNegocioException;
import com.example.ProjetoBiblioteca.mapper.LivroMapper;
import com.example.ProjetoBiblioteca.model.Autor;
import com.example.ProjetoBiblioteca.model.Categoria;
import com.example.ProjetoBiblioteca.model.Livro;
import com.example.ProjetoBiblioteca.repository.AutorRepository;
import com.example.ProjetoBiblioteca.repository.CategoriaRepository;
import com.example.ProjetoBiblioteca.repository.EmprestimoRepository;
import com.example.ProjetoBiblioteca.repository.LivroRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class LivroService extends CrudService<Livro, Long, LivroRequest, LivroResponse> {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final LivroMapper mapper;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        CategoriaRepository categoriaRepository,
                        EmprestimoRepository emprestimoRepository,
                        LivroMapper mapper) {
        super(livroRepository);
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.mapper = mapper;
    }

    // ---------- Conversões exigidas pela classe base ----------

    @Override
    protected Livro toEntity(LivroRequest request) {
        return mapper.toEntity(request);
    }

    @Override
    protected void updateEntity(Livro livro, LivroRequest request) {
        mapper.updateEntity(livro, request);
    }

    @Override
    protected LivroResponse toResponse(Livro livro) {
        return mapper.toResponse(livro);
    }

    // ---------- Criar e atualizar: precisam ligar autor e categorias ----------

    @Override
    @Transactional
    public LivroResponse criar(LivroRequest request) {
        validarIsbnDuplicado(request.isbnLivro());

        Livro livro = mapper.toEntity(request);
        ligarAutorECategorias(livro, request);

        return mapper.toResponse(livroRepository.save(livro));
    }

    @Override
    @Transactional
    public LivroResponse atualizar(Long id, LivroRequest request) {
        Livro livro = buscarEntidade(id);

        // Só valida o ISBN se ele mudou (senão o livro "conflita" consigo mesmo)
        if (request.isbnLivro() != null && !request.isbnLivro().equals(livro.getIsbn())) {
            validarIsbnDuplicado(request.isbnLivro());
        }

        mapper.updateEntity(livro, request);
        ligarAutorECategorias(livro, request);

        return mapper.toResponse(livroRepository.save(livro));
    }

    // ---------- Listagem com filtros (usa um filtro por vez) ----------

    @Transactional(readOnly = true)
    public Page<LivroResponse> listar(String titulo, Long autorId, Long categoriaId, Pageable pageable) {
        Page<Livro> livros;

        if (titulo != null && !titulo.isBlank()) {
            livros = livroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else if (autorId != null) {
            livros = livroRepository.findByAutorId(autorId, pageable);
        } else if (categoriaId != null) {
            livros = livroRepository.findByCategoriasId(categoriaId, pageable);
        } else {
            livros = livroRepository.findAll(pageable);
        }

        return livros.map(this::toResponse);
    }

    // ---------- Remoção ----------

    // Regra: livro com empréstimos no histórico não pode ser removido
    @Override
    @Transactional
    public void deletar(Long id) {
        Livro livro = buscarEntidade(id);

        if (emprestimoRepository.existsByUsuarioId(id)) {
            throw new RegraNegocioException(
                    "Livro possui empréstimos registrados e não pode ser removido");
        }

        livroRepository.delete(livro);
    }

    // ---------- Métodos privados ----------

    private void ligarAutorECategorias(Livro livro, LivroRequest request) {
        Autor autor = autorRepository.findById(request.autorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Autor não encontrado: " + request.autorId()));

        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.size() != request.categoriaIds().size()) {
            throw new RecursoNaoEncontradoException("Alguma categoria informada não existe");
        }

        livro.setAutor(autor);
        livro.setCategorias(new HashSet<>(categorias));
    }

    private void validarIsbnDuplicado(String isbn) {
        if (isbn != null && livroRepository.existsByIsbn(isbn)) {
            throw new RegraNegocioException("Já existe um livro com o ISBN: " + isbn);
        }
    }
}
