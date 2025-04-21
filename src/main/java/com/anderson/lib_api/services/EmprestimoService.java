package com.anderson.lib_api.services;

import com.anderson.lib_api.dto.EmprestimoDto;
import com.anderson.lib_api.models.Aluno;
import com.anderson.lib_api.models.Emprestimo;
import com.anderson.lib_api.models.Livro;
import com.anderson.lib_api.repositories.AlunoRepository;
import com.anderson.lib_api.repositories.EmprestimoRepository;
import com.anderson.lib_api.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public ResponseEntity<?> criar(EmprestimoDto dto) {

        Optional<Aluno> alunoOpt = alunoRepository.findById(dto.id_aluno());
        if (alunoOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Aluno com ID " + dto.id_aluno() + " não encontrado.");
        }

        Optional<Livro> livroOpt = livroRepository.findById(dto.id_livro());
        if (livroOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Livro com ID " + dto.id_livro() + " não encontrado.");
        }

        Aluno aluno = alunoOpt.get();
        Livro livro = livroOpt.get();

        Optional<Emprestimo> emprestimoExistente = repository.findByAlunoAndLivro(aluno, livro);
        if (emprestimoExistente.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Já existe um empréstimo ativo para este aluno e livro.");
        }

        Emprestimo emprestimo = new Emprestimo(aluno, livro);
        emprestimo.set_active(true);
        emprestimo.setData_devolucao(null);
        repository.save(emprestimo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Empréstimo criado com sucesso!");
    }

    public ResponseEntity devolver(UUID id){

        Optional<Emprestimo> optional = repository.findById(id);

        if (optional.isPresent()) {
            Emprestimo emprestimo = optional.get();
            emprestimo.set_active(false);
            emprestimo.setData_devolucao(LocalDate.now());
            repository.save(emprestimo);
            return ResponseEntity.ok("EMPRESTIMO DEVOLVIDO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMPRESTICO COM ID " + id + " NÃO ENCONTRADO!");
        }
    }

    public ResponseEntity<?> buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {

        if (Boolean.TRUE.equals(all)) {
            List<Emprestimo> lista = repository.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM EMPRÉSTIMO ENCONTRADO.");
            }
            return ResponseEntity.ok(lista);
        }

        if (id != null) {
            Optional<Emprestimo> emprestimo = repository.findById(id);
            return emprestimo.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("EMPRÉSTIMO COM ID " + id + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 == null && valor_02 == null) {
            Optional<Emprestimo> resultado = buscarPorAtributo(atributo_01, valor_01);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("EMPRÉSTIMO COM " + atributo_01 + " = " + valor_01 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 == null && valor_01 == null && atributo_02 != null && valor_02 != null) {
            Optional<Emprestimo> resultado = buscarPorAtributo(atributo_02, valor_02);
            return resultado.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("EMPRÉSTIMO COM " + atributo_02 + " = " + valor_02 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 != null && valor_02 != null) {
            List<Emprestimo> todos = repository.findAll();
            List<Emprestimo> filtrados = todos.stream()
                    .filter(e -> compararAtributo(e, atributo_01, valor_01) && compararAtributo(e, atributo_02, valor_02))
                    .toList();

            if (!filtrados.isEmpty()) {
                return ResponseEntity.ok(filtrados);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("NENHUM EMPRÉSTIMO COM " + atributo_01 + " = " + valor_01 + " E " + atributo_02 + " = " + valor_02 + " FOI ENCONTRADO.");
            }
        }

        return ResponseEntity.badRequest().body("REQUISIÇÃO INVÁLIDA. PARÂMETROS INSUFICIENTES OU INCORRETOS.");
    }

    private Optional<Emprestimo> buscarPorAtributo(String atributo, String valor) {
        return repository.findAll().stream()
                .filter(e -> compararAtributo(e, atributo, valor))
                .findFirst();
    }
    private boolean compararAtributo(Emprestimo e, String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "aluno" -> e.getAluno().getNome().equalsIgnoreCase(valor);
            case "livro" -> e.getLivro().getTitulo().equalsIgnoreCase(valor);
            case "data_vencimento" -> e.getData_vencimento().toString().equalsIgnoreCase(valor);
            default -> false;
        };
    }


    public ResponseEntity<?> atualizar(UUID id, EmprestimoDto dto) {
        Optional<Emprestimo> optionalEmprestimo = repository.findById(id);

        if (optionalEmprestimo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMPRÉSTIMO NÃO ENCONTRADO!");
        }

        Emprestimo emprestimo = optionalEmprestimo.get();

        Optional<Aluno> alunoOpt = alunoRepository.findById(dto.id_aluno());
        if (alunoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aluno com ID " + dto.id_aluno() + " não encontrado.");
        }

        Optional<Livro> livroOpt = livroRepository.findById(dto.id_livro());
        if (livroOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livro com ID " + dto.id_livro() + " não encontrado.");
        }

        Aluno aluno = alunoOpt.get();
        Livro livro = livroOpt.get();

        if (!emprestimo.getAluno().getId().equals(dto.id_aluno()) ||
                !emprestimo.getLivro().getId().equals(dto.id_livro())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não é permitido alterar o aluno ou o livro de um empréstimo.");
        }

        emprestimo.setData_vencimento(emprestimo.getData_vencimento().plusDays(7));

        repository.save(emprestimo);
        return ResponseEntity.ok("EMPRÉSTIMO ATUALIZADO COM SUCESSO!");
    }

    public ResponseEntity<?> listarEmprestimosEmAtraso() {
        LocalDate hoje = LocalDate.now();

        List<Emprestimo> atrasados = repository.findAll().stream()
                .filter(e -> e.is_active() && e.getData_vencimento() != null && e.getData_vencimento().isBefore(hoje))
                .toList();

        if (atrasados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum empréstimo em atraso encontrado.");
        }

        return ResponseEntity.ok(atrasados);
    }



    public ResponseEntity<?> excluir(UUID id) {
        Optional<Emprestimo> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("EMPRESTIMO EXCLUIDO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ALUNo COM ID " + id + " NÃO ENCONTRADO!");
        }
    }




}
