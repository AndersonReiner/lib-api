package com.anderson.lib_api.models;

import com.anderson.lib_api.dto.CursoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cursos")
public class Curso {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, name = "carga_horaria")
    private Integer cargaHoraria;

    public Curso(String nome, Integer cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public Curso(CursoDto dto) {
        this.nome = dto.nome();
        this.cargaHoraria = dto.cargaHoraria();
    }

}
