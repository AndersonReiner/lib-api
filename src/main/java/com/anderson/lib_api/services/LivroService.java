package com.anderson.lib_api.services;

import com.anderson.lib_api.dto.LivroDto;
import com.anderson.lib_api.models.Livro;
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
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public ResponseEntity criar(LivroDto dto){

        if (repository.findByTitulo(dto.titulo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("TITULO: " + dto.titulo() + " DE LIVRO JÁ CADASTRADO!");
        }
        else
        {
            repository.save(new Livro(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("LIVRO CADASTRADO COM SUCESSO!!");
        }

    }

    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {

        if (Boolean.TRUE.equals(all)) {
            List<Livro> lista = repository.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM LIVRO ENCONTRADO.");
            }
            return ResponseEntity.ok(lista);
        }


        if (id != null) {
            Optional<Livro> livro = repository.findById(id);
            return livro.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("LIVRO COM ID " + id + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 == null && valor_02 == null) {
            Optional<Livro> resultado = buscarPorAtributo(atributo_01, valor_01);
            return resultado.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("LIVROS COM " + atributo_01 + " = " + valor_01 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 == null && valor_01 == null && atributo_02 != null && valor_02 != null) {
            Optional<Livro> resultado = buscarPorAtributo(atributo_02, valor_02);
            return resultado.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("LIVRO COM " + atributo_02 + " = " + valor_02 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 != null && valor_01 != null && atributo_02 != null && valor_02 != null) {
            List<Livro> todos = repository.findAll();
            List<Livro> filtrados = todos.stream()
                    .filter(a -> compararAtributo(a, atributo_01, valor_01) && compararAtributo(a, atributo_02, valor_02))
                    .toList();

            if (!filtrados.isEmpty()) {
                return ResponseEntity.ok(filtrados);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM LIVRO COM " + atributo_01 + " = " + valor_01 +
                        " E " + atributo_02 + " = " + valor_02 + " FOI ENCONTRADO.");
            }
        }

        return ResponseEntity.badRequest().body("REQUISIÇÃO INVÁLIDA. PARÂMETROS INSUFICIENTES OU INCORRETOS.");
    }

    private Optional<Livro> buscarPorAtributo(String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "titulo" -> repository.findByTitulo(valor);
            case "autor" -> repository.findByAutor(valor);
            case "dataPublic" -> repository.findByDataPublic(LocalDate.parse(valor));
            default -> Optional.empty();
        };
    }


    private boolean compararAtributo(Livro livro, String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "titulo" -> livro.getTitulo().equalsIgnoreCase(valor);
            case "autor" -> livro.getAutor().equalsIgnoreCase(valor);
            case "datapublic" -> livro.getDataPublic().toString().equalsIgnoreCase(valor);
            default -> false;
        };
    }

    public ResponseEntity atualizar(UUID id, LivroDto dto) {

        Optional<Livro> optionalLivro = repository.findById(id);

        if (optionalLivro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LIVRO NÃO ENCONTRADO!");
        }

        Livro livroExistente = optionalLivro.get();

        if (!livroExistente.getTitulo().equals(dto.titulo()) && repository.findByTitulo(dto.titulo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("TITULO: " + dto.titulo() + " JÁ CADASTRADO!");
        }

        livroExistente.setTitulo(dto.titulo());
        livroExistente.setAutor(dto.autor());
        livroExistente.setDataPublic(dto.dataPublic());
        livroExistente.setQuantidade(dto.quantidade());

        repository.save(livroExistente);
        return ResponseEntity.ok("LIVRO ATUALIZADO COM SUCESSO!");
    }

    public ResponseEntity excluir(UUID id) {
        Optional<Livro> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("LIVRO EXCLUIDO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LIVRO COM ID " + id + " NÃO ENCONTRADO!");
        }
    }

}
