package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.EmprestimoRequest;
import com.example.ProjetoBiblioteca.dto.response.EmprestimoResponse;
import com.example.ProjetoBiblioteca.exception.RecursoNaoEncontradoException;
import com.example.ProjetoBiblioteca.exception.RegraNegocioException;
import com.example.ProjetoBiblioteca.mapper.EmprestimoMapper;
import com.example.ProjetoBiblioteca.model.Emprestimo;
import com.example.ProjetoBiblioteca.model.Livro;
import com.example.ProjetoBiblioteca.model.Usuario;
import com.example.ProjetoBiblioteca.model.enums.StatusEmprestimo;
import com.example.ProjetoBiblioteca.repository.EmprestimoRepository;
import com.example.ProjetoBiblioteca.repository.LivroRepository;
import com.example.ProjetoBiblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    public static final int MAX_EMPRESTIMOS_ATIVOS = 3;
    public static final int PRAZO_EM_DIAS = 14;
    public static final BigDecimal MULTA_POR_DIA = new BigDecimal("2.00");

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoMapper mapper;

    // ---------- Registrar empréstimo ----------

    @Transactional
    public EmprestimoResponse registrar(EmprestimoRequest request) {
        Usuario usuario = buscarUsuario(request.usuarioId());
        Livro livro = buscarLivro(request.livroId());

        validarLimiteDeEmprestimos(usuario);
        validarLivroDisponivel(livro);

        LocalDate hoje = LocalDate.now();

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(hoje);
        emprestimo.setDataPrevistaDevolucao(hoje.plusDays(PRAZO_EM_DIAS));
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        livro.setDisponivel(false);

        return mapper.toResponse(emprestimoRepository.save(emprestimo));
    }

    // ---------- Devolver ----------

    @Transactional
    public EmprestimoResponse devolver(Long id) {
        Emprestimo emprestimo = buscarEntidade(id);

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new RegraNegocioException("Este empréstimo já foi devolvido");
        }

        LocalDate hoje = LocalDate.now();

        emprestimo.setDataDevolucao(hoje);
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setMulta(calcularMulta(emprestimo.getDataPrevistaDevolucao(), hoje));

        emprestimo.getLivro().setDisponivel(true);

        return mapper.toResponse(emprestimoRepository.save(emprestimo));
    }

    // ---------- Consultas ----------

    @Transactional(readOnly = true)
    public Page<EmprestimoResponse> listar(Long usuarioId, Pageable pageable) {
        Page<Emprestimo> emprestimos = (usuarioId == null)
                ? emprestimoRepository.findAll(pageable)
                : emprestimoRepository.findByUsuarioId(usuarioId, pageable);

        return emprestimos.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmprestimoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<EmprestimoResponse> listarAtrasados() {
        return emprestimoRepository.findAtrasados(LocalDate.now()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ---------- Regras de negócio ----------

    private void validarLimiteDeEmprestimos(Usuario usuario) {
        long ativos = emprestimoRepository
                .countByUsuarioIdAndStatus(usuario.getId(), StatusEmprestimo.ATIVO);

        if (ativos >= MAX_EMPRESTIMOS_ATIVOS) {
            throw new RegraNegocioException(
                    "O usuário já possui " + MAX_EMPRESTIMOS_ATIVOS + " empréstimos ativos");
        }
    }

    private void validarLivroDisponivel(Livro livro) {
        if (!livro.isDisponivel()) {
            throw new RegraNegocioException("O livro já está emprestado");
        }
    }

    private BigDecimal calcularMulta(LocalDate dataPrevista, LocalDate dataDevolucao) {
        long diasDeAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);

        if (diasDeAtraso <= 0) {
            return BigDecimal.ZERO;
        }
        return MULTA_POR_DIA.multiply(BigDecimal.valueOf(diasDeAtraso));
    }

    // ---------- Buscas auxiliares (404 se não existir) ----------

    private Emprestimo buscarEntidade(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Empréstimo não encontrado: " + id));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado: " + id));
    }

    private Livro buscarLivro(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Livro não encontrado: " + id));
    }
}
