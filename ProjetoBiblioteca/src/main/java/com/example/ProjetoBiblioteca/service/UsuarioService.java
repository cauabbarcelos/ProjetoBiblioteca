package com.example.ProjetoBiblioteca.service;

import com.example.ProjetoBiblioteca.dto.request.UsuarioRequest;
import com.example.ProjetoBiblioteca.dto.response.UsuarioResponse;
import com.example.ProjetoBiblioteca.exception.RegraNegocioException;
import com.example.ProjetoBiblioteca.mapper.UsuarioMapper;
import com.example.ProjetoBiblioteca.model.Usuario;
import com.example.ProjetoBiblioteca.repository.EmprestimoRepository;
import com.example.ProjetoBiblioteca.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService extends CrudService<Usuario, Long, UsuarioRequest, UsuarioResponse> {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioMapper mapper;

    public UsuarioService(JpaRepository<Usuario, Long> repository, EmprestimoRepository emprestimoRepository, UsuarioRepository usuarioRepository, UsuarioMapper mapper) {
        super(repository);
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    // ---------- Conversões exigidas pela classe base ----------

    @Override
    protected Usuario toEntity(UsuarioRequest request) {
        return mapper.toEntity(request);
    }

    @Override
    protected void updateEntity(Usuario usuario, UsuarioRequest request) {
        mapper.updateEntity(usuario, request);
    }

    @Override
    protected UsuarioResponse toResponse(Usuario usuario) {
        return mapper.toResponse(usuario);
    }

    // ---------- O que é específico do usuário ----------

    // Regra: não pode haver dois usuários com o mesmo e-mail
    @Override
    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        validarEmailDuplicado(request.email());
        return super.criar(request);
    }

    // Na atualização, só valida se o e-mail mudou
    @Override
    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario atual = buscarEntidade(id);

        if (!atual.getEmail().equalsIgnoreCase(request.email())) {
            validarEmailDuplicado(request.email());
        }

        return super.atualizar(id, request);
    }

    // Listagem com filtro opcional por nome
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listar(String nome, Pageable pageable) {
        if (nome == null || nome.isBlank()) {
            return listar(nome, pageable);
        }
        return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::toResponse);
    }

    // Regra: usuário com empréstimos (ativos ou no histórico) não pode ser removido
    @Override
    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntidade(id);

        if (emprestimoRepository.existsByUsuarioId(id)) {
            throw new RegraNegocioException(
                    "Usuário possui empréstimos registrados e não pode ser removido");
        }

        usuarioRepository.delete(usuario);
    }

    private void validarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraNegocioException("Já existe um usuário com o e-mail: " + email);
        }
    }
}

