package com.anderson.lib_api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anderson.lib_api.dto.AdministradorDto;
import com.anderson.lib_api.models.Administrador;
import com.anderson.lib_api.repositories.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository repository;
    

    public ResponseEntity criar(AdministradorDto dto) {
        
    
        if (repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NOME: " + dto.nome() + " DA PESSOA JÁ CADASTRADA!");
        }
    
        if (repository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("EMAIL: " + dto.email() + " DE USUÁRIO JÁ CADASTRADO!");
        }
    
        if (repository.findByUsuario(dto.usuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("USUÁRIO: " + dto.usuario() + " JÁ CADASTRADO!");
        }
    
        if (repository.findByCpf(dto.cpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: " + dto.cpf() + " JÁ CADASTRADO!");
        }

        else
        {
            repository.save(new Administrador(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body("USUÁRIO CADASTRADO COM SUCESSO!!");
        }

    }


    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {

        if (Boolean.TRUE.equals(all)) {
            List<Administrador> lista = repository.findAll();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM ADMINISTRADOR ENCONTRADO.");
            }
            return ResponseEntity.ok(lista);
        }
    

        if (id != null) {
            Optional<Administrador> admin = repository.findById(id);
            return admin.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("ADMINISTRADOR COM ID " + id + " NÃO ENCONTRADO."));
        }
    
        if (atributo_01 != null && valor_01 != null && atributo_02 == null && valor_02 == null) {
            Optional<Administrador> resultado = buscarPorAtributo(atributo_01, valor_01);
            return resultado.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("ADMINISTRADOR COM " + atributo_01 + " = " + valor_01 + " NÃO ENCONTRADO."));
        }

        if (atributo_01 == null && valor_01 == null && atributo_02 != null && valor_02 != null) {
            Optional<Administrador> resultado = buscarPorAtributo(atributo_02, valor_02);
            return resultado.<ResponseEntity>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ADMINISTRADOR COM " + atributo_02 + " = " + valor_02 + " NÃO ENCONTRADO."));
        }
    
        if (atributo_01 != null && valor_01 != null && atributo_02 != null && valor_02 != null) {
            List<Administrador> todos = repository.findAll();
            List<Administrador> filtrados = todos.stream()
                    .filter(a -> compararAtributo(a, atributo_01, valor_01) && compararAtributo(a, atributo_02, valor_02))
                    .toList();
    
            if (!filtrados.isEmpty()) {
                return ResponseEntity.ok(filtrados);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM ADMINISTRADOR COM " + atributo_01 + " = " + valor_01 +
                        " E " + atributo_02 + " = " + valor_02 + " FOI ENCONTRADO.");
            }
        }
    
        return ResponseEntity.badRequest().body("REQUISIÇÃO INVÁLIDA. PARÂMETROS INSUFICIENTES OU INCORRETOS.");
    }


    private Optional<Administrador> buscarPorAtributo(String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> repository.findByNome(valor);
            case "email" -> repository.findByEmail(valor);
            case "cpf" -> repository.findByCpf(valor);
            case "usuario" -> repository.findByUsuario(valor);
            default -> Optional.empty();
        };
    }
    

    private boolean compararAtributo(Administrador adm, String atributo, String valor) {
        return switch (atributo.toLowerCase()) {
            case "nome" -> adm.getNome().equalsIgnoreCase(valor);
            case "email" -> adm.getEmail().equalsIgnoreCase(valor);
            case "cpf" -> adm.getCpf().equalsIgnoreCase(valor);
            case "usuario" -> adm.getUsuario().equalsIgnoreCase(valor);
            default -> false;
        };
    }
    

    public ResponseEntity atualizar(UUID id, AdministradorDto dto) {

        Optional<Administrador> optionalAdm = repository.findById(id);
    
        if (optionalAdm.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ADMINISTRADOR NÃO ENCONTRADO!");
        }
    
        Administrador admExistente = optionalAdm.get();
    
        if (!admExistente.getNome().equals(dto.nome()) && repository.findByNome(dto.nome()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NOME: " + dto.nome() + " JÁ CADASTRADO!");
        }
    
        if (!admExistente.getEmail().equals(dto.email()) && repository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("EMAIL: " + dto.email() + " JÁ CADASTRADO!");
        }
    
        if (!admExistente.getUsuario().equals(dto.usuario()) && repository.findByUsuario(dto.usuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("USUÁRIO: " + dto.usuario() + " JÁ CADASTRADO!");
        }
    
        if (!admExistente.getCpf().equals(dto.cpf()) && repository.findByCpf(dto.cpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: " + dto.cpf() + " JÁ CADASTRADO!");
        }
    
        admExistente.setNome(dto.nome());
        admExistente.setEmail(dto.email());
        admExistente.setDataNasc(dto.dataNasc());
        admExistente.setCpf(dto.cpf());
        admExistente.setUsuario(dto.usuario());
        admExistente.setSenha(dto.senha());
    
        repository.save(admExistente);
        return ResponseEntity.ok("ADMINISTRADOR ATUALIZADO COM SUCESSO!");
    }
    
    
    public ResponseEntity deletar(UUID id) {
        
        Optional<Administrador> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("ADMINISTRADOR DELETADO COM SUCESSO!");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ADMINISTRADOR COM ID " + id + " NÃO ENCONTRADO!");
        }
    }
    

}