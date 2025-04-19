package com.anderson.lib_api.services;

import com.anderson.lib_api.dto.CursoDto;
import com.anderson.lib_api.models.Curso;
import com.anderson.lib_api.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    public ResponseEntity criar(CursoDto dto){

        if (repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NOME: " + dto.nome() + " DE CURSO JÁ CADASTRADO!");
        }
        else
        {
            repository.save(new Curso(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("CURSO CADASTRADO COM SUCESSO!!");
        }

    }

    public ResponseEntity<?> buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {

        if (Boolean.TRUE.equals(all)) {
            List<Curso> lista = repository.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM CURSO ENCONTRADO.");
            }
            return ResponseEntity.ok(lista);
        }

        if (id != null) {
            Optional<Curso> curso = repository.findById(id);
            return curso.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("CURSO COM ID " + id + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 == null && valor_02 == null) {
            Optional<Curso> resultado = buscarPorAtributo(atributo_01, valor_01);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("CURSO COM " + atributo_01 + " = " + valor_01 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 == null && valor_01 == null && atributo_02 != null && valor_02 != null) {
            Optional<Curso> resultado = buscarPorAtributo(atributo_02, valor_02);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("CURSO COM " + atributo_02 + " = " + valor_02 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 != null && valor_02 != null) {
            List<Curso> todos = repository.findAll();
            List<Curso> filtrados = todos.stream()
                    .filter(a -> compararAtributo(a, atributo_01, valor_01) && compararAtributo(a, atributo_02, valor_02))
                    .toList();

            if (!filtrados.isEmpty()) {
                return ResponseEntity.ok(filtrados);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM CURSO COM " + atributo_01 + " = " + valor_01 +
                        " E " + atributo_02 + " = " + valor_02 + " FOI ENCONTRADO.");
            }
        }

        return ResponseEntity.badRequest().body("REQUISIÇÃO INVÁLIDA. PARÂMETROS INSUFICIENTES OU INCORRETOS.");
    }



    private Optional<Curso> buscarPorAtributo(String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> repository.findByNome(valor);
            case "cargaHoraria" -> repository.findByCargaHoraria(Integer.parseInt(valor));
            default -> Optional.empty();
        };
    }


    private boolean compararAtributo(Curso curso, String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> curso.getNome().equalsIgnoreCase(valor);
            case "cargaHoraria" -> String.valueOf(curso.getCargaHoraria()).equals(valor);
            default -> false;
        };
    }


    public ResponseEntity atualizar(UUID id, CursoDto dto) {

        Optional<Curso> optionalCurso = repository.findById(id);

        if (optionalCurso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CURSO NÃO ENCONTRADO!");
        }

        Curso cursoExistente = optionalCurso.get();

        if (!cursoExistente.getNome().equals(dto.nome()) && repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CURSO: " + dto.nome() + " JÁ CADASTRADO!");
        }

        cursoExistente.setNome(dto.nome());
        cursoExistente.setCargaHoraria(dto.cargaHoraria());

        repository.save(cursoExistente);
        return ResponseEntity.ok("CURSO ATUALIZADO COM SUCESSO!");
    }

    public ResponseEntity<?> excluir(UUID id) {
        Optional<Curso> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("CURSO EXCLUIDO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CURSO COM ID " + id + " NÃO ENCONTRADO!");
        }
    }
}
