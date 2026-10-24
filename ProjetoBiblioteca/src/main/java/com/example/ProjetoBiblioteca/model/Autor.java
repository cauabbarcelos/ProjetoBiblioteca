package com.example.ProjetoBiblioteca.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Getter

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)

    private String nome;

    private String nacionalidade;

    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "autor")

    private List<Livro> livros =  new ArrayList<>();
}
