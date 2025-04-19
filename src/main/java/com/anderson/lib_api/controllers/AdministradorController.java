package com.anderson.lib_api.controllers;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.lib_api.dto.AdministradorDto;
import com.anderson.lib_api.services.AdministradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/administrador")
public class AdministradorController extends BaseController<AdministradorDto> {


    @Autowired
    public AdministradorService service;

    @Override
    @Operation(summary = "Cria um novo administrador", description = "Cadastra um novo administrador com base nos dados fornecidos.")
    public ResponseEntity criar(@RequestBody @Valid AdministradorDto dto) {

        return service.criar(dto);

    }

    @Override
    @Operation(summary = "Busca administradores", description = "Permite buscar administradores por ID, atributos específicos ou retornar todos.")
    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    @Operation(summary = "Atualiza um administrador", description = "Atualiza os dados de um administrador a partir do ID e do DTO fornecido.")
    public ResponseEntity atualizar(UUID id, @RequestBody @Valid AdministradorDto dto) {

        return service.atualizar(id, dto);
    }

    @Override
    @Operation(summary = "Exclui um administrador", description = "Remove um administrador com base no ID fornecido.")
    public ResponseEntity excluir(UUID id) {

        return service.deletar(id);

    }
    
}
