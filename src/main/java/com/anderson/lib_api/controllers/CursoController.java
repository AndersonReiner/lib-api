package com.anderson.lib_api.controllers;

import ch.qos.logback.core.joran.event.SaxEventRecorder;
import com.anderson.lib_api.dto.CursoDto;
import com.anderson.lib_api.services.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/curso/")
public class CursoController extends BaseController<CursoDto>{

    @Autowired
    private CursoService service;

    @Override
    @Operation(summary = "Cria um novo curso", description = "Cadastra um novo curso com base nos dados fornecidos.")
    public ResponseEntity criar(@RequestBody @Valid CursoDto dto) {
        return service.criar(dto);
    }

    @Override
    @Operation(summary = "Busca curso", description = "Permite buscar curso por ID, atributos específicos ou retornar todos.")
    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    @Operation(summary = "Atualiza um curso", description = "Atualiza os dados de um curso a partir do ID e do DTO fornecido.")
    public ResponseEntity atualizar(UUID id, @RequestBody @Valid CursoDto dto) {
        return service.atualizar(id, dto);
    }

    @Override
    @Operation(summary = "Exclui um curso", description = "Remove um curso com base no ID fornecido.")
    public ResponseEntity excluir(UUID id) {
        return service.excluir(id);
    }
}
