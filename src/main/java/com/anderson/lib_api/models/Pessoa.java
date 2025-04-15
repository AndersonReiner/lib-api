package com.anderson.lib_api.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas")
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, name = "nome_pessoa", nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column (nullable = false, name = "data_nascimento")
    private LocalDate dataNasc;

    @Column(unique = true, nullable = false)
    private String cpf;

    public Pessoa(String nome, String email,LocalDate dataNasc, String cpf) {
        this.nome = nome;
        this.email = email;
        this.dataNasc = dataNasc;
        this.cpf = cpf;
    }

}
