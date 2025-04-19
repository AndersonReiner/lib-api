package com.anderson.lib_api.repositories;

import com.anderson.lib_api.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    Optional<Livro> findByTitulo(String titulo);
    Optional<Livro> findByAutor(String autor);
    Optional<Livro> findByDataPublic(LocalDate dataPublic);

}
