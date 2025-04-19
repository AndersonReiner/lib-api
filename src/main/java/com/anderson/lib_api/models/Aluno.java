package com.anderson.lib_api.models;

import com.anderson.lib_api.dto.AlunoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private UUID id_curso;

    @Column(nullable = false, unique = true)
    private Integer matricula;

    @Column(nullable = false, unique = true)
    private String telefone;

    public Aluno(String nome, String email, LocalDate dataNasc, String cpf, UUID id_curso, Integer matricula, String telefone) {
        super(nome, email, dataNasc, cpf);
        this.id_curso = id_curso;
        this.matricula = matricula;
        this.telefone = telefone;
    }

    public Aluno(AlunoDto dto) {
        super(dto.nome(), dto.email(), dto.dataNasc(), dto.cpf());
        this.id_curso = dto.id_curso();
        this.matricula = dto.matricula();
        this.telefone = dto.telefone();
    }

}
