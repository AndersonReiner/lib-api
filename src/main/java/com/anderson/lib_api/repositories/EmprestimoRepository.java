package com.anderson.lib_api.repositories;

import com.anderson.lib_api.models.Aluno;
import com.anderson.lib_api.models.Emprestimo;
import com.anderson.lib_api.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    Optional<Emprestimo> findByAluno(Aluno aluno);
    Optional<Emprestimo> findByLivro(Livro livro);
    List<Emprestimo> findByAluno_Id(UUID idAluno);
    List<Emprestimo> findByLivro_Id(UUID idLivro);
    Optional<Emprestimo> findByAlunoAndLivro(Aluno aluno, Livro livro);

}
