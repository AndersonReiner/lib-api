package com.anderson.lib_api.services;

import com.anderson.lib_api.dto.AlunoDto;
import com.anderson.lib_api.models.Aluno;
import com.anderson.lib_api.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    public ResponseEntity criar(AlunoDto dto) {


        if (repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NOME: " + dto.nome() + " DA PESSOA JÁ CADASTRADA!");
        }

        if (repository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("EMAIL: " + dto.email() + " DE USUÁRIO JÁ CADASTRADO!");
        }

        if (repository.findByMatricula(dto.matricula()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("MATRICULA: " + dto.matricula() + " JÁ CADASTRADA!");
        }

        if (repository.findByCpf(dto.cpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: " + dto.cpf() + " JÁ CADASTRADO!");
        }
        if (repository.findByTelefone(dto.telefone()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("TELEFONE: " + dto.telefone() + " JÁ CADASTRADO!");
        }
        else
        {
            repository.save(new Aluno(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("ALUNO CADASTRADO COM SUCESSO!!");
        }

    }

    public ResponseEntity<?> buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {

        if (Boolean.TRUE.equals(all)) {
            List<Aluno> lista = repository.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM ALUNO ENCONTRADO.");
            }
            return ResponseEntity.ok(lista);
        }

        if (id != null) {
            Optional<Aluno> aluno = repository.findById(id);
            return aluno.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("ALUNO COM ID " + id + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 == null && valor_02 == null) {
            Optional<Aluno> resultado = buscarPorAtributo(atributo_01, valor_01);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("ALUNO COM " + atributo_01 + " = " + valor_01 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 == null && valor_01 == null && atributo_02 != null && valor_02 != null) {
            Optional<Aluno> resultado = buscarPorAtributo(atributo_02, valor_02);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("ALUNO COM " + atributo_02 + " = " + valor_02 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 != null && valor_02 != null) {
            List<Aluno> todos = repository.findAll();
            List<Aluno> filtrados = todos.stream()
                    .filter(a -> compararAtributo(a, atributo_01, valor_01) && compararAtributo(a, atributo_02, valor_02))
                    .toList();

            if (!filtrados.isEmpty()) {
                return ResponseEntity.ok(filtrados);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM ALUNO COM " + atributo_01 + " = " + valor_01 +
                        " E " + atributo_02 + " = " + valor_02 + " FOI ENCONTRADO.");
            }
        }

        return ResponseEntity.badRequest().body("REQUISIÇÃO INVÁLIDA. PARÂMETROS INSUFICIENTES OU INCORRETOS.");
    }



    private Optional<Aluno> buscarPorAtributo(String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> repository.findByNome(valor);
            case "email" -> repository.findByEmail(valor);
            case "cpf" -> repository.findByCpf(valor);
            case "matricula" -> repository.findByMatricula(Integer.valueOf(valor));
            case "telefone" -> repository.findByTelefone(valor);
            default -> Optional.empty();
        };
    }


    private boolean compararAtributo(Aluno aluno, String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> aluno.getNome().equalsIgnoreCase(valor);
            case "email" -> aluno.getEmail().equalsIgnoreCase(valor);
            case "cpf" -> aluno.getCpf().equalsIgnoreCase(valor);
            case "matricula" -> String.valueOf(aluno.getMatricula()).equals(valor);
            case "telefone" -> aluno.getTelefone().equalsIgnoreCase(valor);
            default -> false;
        };
    }

    public ResponseEntity atualizar(UUID id, AlunoDto dto) {

        Optional<Aluno> optionalAluno = repository.findById(id);

        if (optionalAluno.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ALUNO NÃO ENCONTRADO!");
        }

        Aluno alunoExistente = optionalAluno.get();

        if (!alunoExistente.getNome().equals(dto.nome()) && repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NOME: " + dto.nome() + " JÁ CADASTRADO!");
        }

        if (!alunoExistente.getEmail().equals(dto.email()) && repository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("EMAIL: " + dto.email() + " JÁ CADASTRADO!");
        }

        if (!alunoExistente.getMatricula().equals(dto.matricula()) && repository.findByMatricula(dto.matricula()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("MATRICULA: " + dto.matricula() + " JÁ CADASTRADA!");
        }

        if (!alunoExistente.getCpf().equals(dto.cpf()) && repository.findByCpf(dto.cpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: " + dto.cpf() + " JÁ CADASTRADO!");
        }
        if (!alunoExistente.getTelefone().equals(dto.telefone()) && repository.findByTelefone(dto.telefone()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("TELEFONE: " + dto.telefone() + " JÁ CADASTRADO!");
        }

        alunoExistente.setNome(dto.nome());
        alunoExistente.setEmail(dto.email());
        alunoExistente.setDataNasc(dto.dataNasc());
        alunoExistente.setCpf(dto.cpf());
        alunoExistente.setMatricula(dto.matricula());
        alunoExistente.setTelefone(dto.telefone());

        repository.save(alunoExistente);
        return ResponseEntity.ok("ALUNO ATUALIZADO COM SUCESSO!");
    }


    public ResponseEntity<?> excluir(UUID id) {
        Optional<Aluno> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("ALUNO EXCLUIDO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ALUNo COM ID " + id + " NÃO ENCONTRADO!");
        }
    }
}
