package com.anderson.lib_api.repositories;

import com.anderson.lib_api.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    Optional<Curso> findByNome(String nome);
    Optional<Curso> findByCargaHoraria(Integer cargaHoraria);

}
