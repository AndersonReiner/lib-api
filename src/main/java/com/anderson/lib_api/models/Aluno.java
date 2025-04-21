package com.anderson.lib_api.models;

import com.anderson.lib_api.dto.AlunoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Aluno extends Pessoa {

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @Column(nullable = false, unique = true)
    private Integer matricula;

    @Column(nullable = false, unique = true)
    private String telefone;

    public Aluno(String nome, String email, LocalDate dataNasc, String cpf, Curso curso, Integer matricula, String telefone) {
        super(nome, email, dataNasc, cpf);
        this.curso = curso;
        this.matricula = matricula;
        this.telefone = telefone;
    }

    public Aluno(AlunoDto dto, Curso curso) {
        super(dto.nome(), dto.email(), dto.dataNasc(), dto.cpf());
        this.curso = curso;
        this.matricula = dto.matricula();
        this.telefone = dto.telefone();
    }

}
