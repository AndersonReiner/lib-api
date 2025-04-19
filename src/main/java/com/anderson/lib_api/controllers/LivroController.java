package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.LivroDto;
import com.anderson.lib_api.services.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/livro/")
public class LivroController extends BaseController<LivroDto>{
    
    @Autowired
    private LivroService service;

    @Override
    @Operation(summary = "Cria um novo livro", description = "Cadastra um novo livro com base nos dados fornecidos.")
    public ResponseEntity criar(@RequestBody @Valid LivroDto dto) {
        return service.criar(dto);
    }

    @Override
    @Operation(summary = "Busca livro", description = "Permite buscar livro por ID, atributos específicos ou retornar todos.")
    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    @Operation(summary = "Atualiza um livro", description = "Atualiza os dados de um livro a partir do ID e do DTO fornecido.")
    public ResponseEntity atualizar(UUID id, @RequestBody @Valid LivroDto dto) {
        return service.atualizar(id, dto);
    }

    @Override
    @Operation(summary = "Exclui um livro", description = "Remove um livro com base no ID fornecido.")
    public ResponseEntity excluir(UUID id) {
        return service.excluir(id);
    }
}
