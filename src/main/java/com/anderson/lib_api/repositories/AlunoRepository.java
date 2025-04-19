package com.anderson.lib_api.repositories;

import com.anderson.lib_api.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    Optional<Aluno> findByNome(String nome);
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByTelefone(String telefone);
    Optional<Aluno> findByMatricula(Integer matricula);

}
