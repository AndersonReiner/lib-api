package com.anderson.lib_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    private LocalDate data_vencimento;

    private LocalDate data_devolucao;

    @Column(nullable = false)
    private boolean is_active;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_livro", referencedColumnName = "id")
    private Livro livro;

    @PrePersist
    public void prePersist() {
        this.created_at = LocalDateTime.now();
        this.data_vencimento = this.created_at.toLocalDate().plusDays(7);
    }

    public Emprestimo(Aluno aluno, Livro livro, LocalDateTime created_at, LocalDate data_vencimento) {
        this.aluno = aluno;
        this.livro = livro;
        this.created_at = created_at;
        this.data_vencimento = data_vencimento;
    }

    public Emprestimo(Aluno aluno, Livro livro) {
        this.aluno = aluno;
        this.livro = livro;
        this.created_at = LocalDateTime.now();
        this.data_vencimento = this.created_at.toLocalDate().plusDays(7);
    }
}

