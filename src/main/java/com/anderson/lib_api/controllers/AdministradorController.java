package com.anderson.lib_api.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.lib_api.dto.AdministradorDto;
import com.anderson.lib_api.services.AdministradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("administrador")
public class AdministradorController extends BaseController<AdministradorDto> {


    @Autowired
    public AdministradorService service;

    @Override
    public ResponseEntity criar(@Valid AdministradorDto dto) {

        return service.criar(dto);

    }

    @Override
    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    public ResponseEntity atualizar(UUID id, @Valid AdministradorDto dto) {

        return service.atualizar(id, dto);
    }

    @Override
    public ResponseEntity excluir(UUID id) {

        return service.deletar(id);

    }
    
}
