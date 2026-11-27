package com.example.ProjetoBiblioteca.repository;

import com.example.ProjetoBiblioteca.model.Emprestimo;
import com.example.ProjetoBiblioteca.model.enums.StatusEmprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Regra: no máximo 3 empréstimos ativos por usuário
    long countByUsuarioIdAndStatus(Long usuarioId, StatusEmprestimo status);

    // Para saber se um livro está emprestado agora
    boolean existsByLivroIdAndStatus();

    // Para impedir remover um usuário que tem empréstimos
    boolean existsByUsuarioId(Long usuarioId);

    // Histórico de um usuário
    Page<Emprestimo> findByUsuarioId(Long usuarioId, Pageable pageable);

    // Empréstimos ativos cuja data prevista já passou
    @Query("""
           SELECT e FROM Emprestimo e
           WHERE e.status = com.example.ProjetoBiblioteca.model.enums.StatusEmprestimo.ATIVO
             AND e.dataPrevistaDevolucao < :hoje
           """)
    List<Emprestimo> findAtrasados(@Param("hoje") LocalDate hoje);
}
