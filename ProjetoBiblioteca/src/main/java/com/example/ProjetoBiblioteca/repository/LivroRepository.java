package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Busca parcial pelo título, sem diferenciar maiúsculas de minúsculas
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Livros de um autor
    Page<Livro> findByAutorId(Long autorId, Pageable pageable);

    // Livros de uma categoria (percorre a lista "categorias" da entidade)
    Page<Livro> findByCategoriasId(Long categoriaId, Pageable pageable);

    // Só os livros que podem ser emprestados agora
    Page<Livro> findByDisponivelTrue(Pageable pageable);

    // Para impedir dois livros com o mesmo ISBN
    boolean existsByIsbn(String isbn);

    // Para impedir remover um autor que ainda tem livros
    boolean existsByAutorId(Long autorId);

    // Para impedir remover uma categoria que ainda tem livros
    boolean existsByCategoriasId(Long categoriaId);
}