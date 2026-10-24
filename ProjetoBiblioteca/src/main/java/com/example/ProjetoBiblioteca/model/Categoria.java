package com.example.ProjetoBiblioteca.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Entity
@Setter
@NoArgsConstructor
@Getter

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false , unique = true)

    private String nome;

    private String descricao;

    @ManyToMany(mappedBy = "categorias")
    private Set<Livro> livros = new HashSet<>();
}
