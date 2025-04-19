package com.anderson.lib_api.models;

import com.anderson.lib_api.dto.LivroDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private LocalDate dataPublic;

    @Column(nullable = false)
    private Integer quantidade;

    public Livro(String titulo, String autor, LocalDate dataPublic, Integer quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.dataPublic = dataPublic;
        this.quantidade = quantidade;
    }

    public Livro(LivroDto dto) {
        this.titulo = dto.titulo();
        this.autor = dto.autor();
        this.dataPublic = dto.dataPublic();
        this.quantidade = dto.quantidade();
    }


}
