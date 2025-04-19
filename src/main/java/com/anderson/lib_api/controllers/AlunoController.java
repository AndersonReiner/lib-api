package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.AlunoDto;
import com.anderson.lib_api.services.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/aluno/")
public class AlunoController extends BaseController<AlunoDto>{

    @Autowired
    private AlunoService service;

    @Override
    @Operation(summary = "Cria um novo aluno", description = "Cadastra um novo aluno com base nos dados fornecidos.")
    public ResponseEntity<?> criar(AlunoDto dto) {return service.criar(dto);}

    @Override
    @Operation(summary = "Busca aluno", description = "Permite buscar aluno por ID, atributos específicos ou retornar todos.")
    public ResponseEntity<?> buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    @Operation(summary = "Atualiza um aluno", description = "Atualiza os dados de um aluno a partir do ID e do DTO fornecido.")
    public ResponseEntity<?> atualizar(UUID id, AlunoDto dto) {
        return service.atualizar(id, dto);
    }

    @Override
    @Operation(summary = "Exclui um aluno", description = "Remove um aluno com base no ID fornecido.")
    public ResponseEntity<?> excluir(UUID id) {
        return service.excluir(id);
    }
}
