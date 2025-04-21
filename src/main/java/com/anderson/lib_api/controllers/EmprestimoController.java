package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.EmprestimoDto;
import com.anderson.lib_api.services.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/emprestimo")
public class EmprestimoController extends BaseController<EmprestimoDto>{

    @Autowired
    private EmprestimoService service;

    @Override
    public ResponseEntity criar(EmprestimoDto dto) {
        return service.criar(dto);
    }

    @PostMapping("devolver")
    public ResponseEntity devolver(@RequestHeader @Valid UUID id){
        return service.devolver(id);
    }

    @Override
    public ResponseEntity buscar(Boolean all, UUID id, String atributo_01, String valor_01, String atributo_02, String valor_02) {
        return service.buscar(all, id, atributo_01, valor_01, atributo_02, valor_02);
    }

    @Override
    public ResponseEntity atualizar(UUID id, EmprestimoDto dto) {
        return service.atualizar(id, dto);
    }

    @GetMapping("atrasos")
    public ResponseEntity emprestimosEmAtraso(){
        return service.listarEmprestimosEmAtraso();
    }


    @Override
    public ResponseEntity excluir(UUID id) {
        return service.excluir(id);
    }
}
