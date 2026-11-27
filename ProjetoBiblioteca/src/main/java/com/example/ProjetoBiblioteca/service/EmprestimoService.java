package com.example.ProjetoBiblioteca.service;
import com.example.ProjetoBiblioteca.dto.request.EmprestimoRequest;
import com.example.ProjetoBiblioteca.dto.response.EmprestimoResponse;
import com.example.ProjetoBiblioteca.mapper.EmprestimoMapper;
import com.example.ProjetoBiblioteca.model.Emprestimo;
import com.example.ProjetoBiblioteca.model.Livro;
import com.example.ProjetoBiblioteca.model.Usuario;
import com.example.ProjetoBiblioteca.model.enums.StatusEmprestimo;
import com.example.ProjetoBiblioteca.repository.EmprestimoRepository;
import com.example.ProjetoBiblioteca.repository.LivroRepository;
import com.example.ProjetoBiblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmprestimoService{

    public static final int MAX_EMPRESTIMOS_ATIVOS = 3;
    public static final int PRAZO_EM_DIAS = 14;
    public static final BigDecimal MULTA_POR_DIA = new BigDecimal("2.00");

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoMapper mapper;

    //------------------REGISTRAR EMPRESTIMOS--------------------//
     @Transactional

    public EmprestimoResponse registrar(EmprestimoRequest request){
         Usuario usuario =


         emprestimoRepository.existsByLivroIdAndStatus();

         LocalDate hoje = LocalDate.now();

         Emprestimo emprestimo = new Emprestimo();
         emprestimo.setUsuario(usuario);
         emprestimo.setLivro(livro);
         emprestimo.setDataEmprestimo(hoje);
         emprestimo.setDataPrevistaDevolucao(hoje.plusDays(PRAZO_EM_DIAS));


         livro.setDisponivel(false);

         return mapper.toResponse(emprestimoRepository.save(emprestimo));
     }


}
