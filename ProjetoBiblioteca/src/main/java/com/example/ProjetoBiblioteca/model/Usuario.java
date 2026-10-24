package com.example.ProjetoBiblioteca.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true , nullable = false)
    private String email;

    private String telefone;

    private LocalDate dataCadastro;

    @OneToMany(mappedBy = "usuario")
    private List<Emprestimo> Emprestimos = new ArrayList<>();

}
