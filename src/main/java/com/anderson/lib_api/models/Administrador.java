package com.anderson.lib_api.models;

import java.time.LocalDate;

import com.anderson.lib_api.dto.AdministradorDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "administradores")
public class Administrador extends Pessoa{

    @Column(length = 50, name = "nome_usuario",unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String senha;

    public Administrador(String nome, String email,LocalDate dataNasc, String cpf, String usuario, String senha) {
        super(nome, email, dataNasc, cpf);
        this.usuario = usuario;
        this.senha = senha;
    }

    public Administrador(AdministradorDto dto){
        super(dto.nome(), dto.email() ,dto.dataNasc(), dto.cpf());
        this.usuario = dto.usuario();
        this.senha = dto.senha();
    }

}
