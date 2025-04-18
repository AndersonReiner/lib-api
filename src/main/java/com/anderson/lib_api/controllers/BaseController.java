package com.anderson.lib_api.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

public abstract class BaseController<D> {
    
    @PostMapping("criar")
    public abstract ResponseEntity criar(@RequestBody @Valid D dto);
    
    @GetMapping("buscar")
    public abstract ResponseEntity buscar(@RequestParam (value = "all", required = false, defaultValue = "false") Boolean all,
                                    @RequestParam(value = "id", required = false) UUID id,
                                    @RequestParam(value = "atributo_1", required = false) String atributo_01,
                                    @RequestParam(value = "valor_1", required = false) String valor_01,
                                    @RequestParam(value = "atributo_2", required = false) String atributo_02,
                                    @RequestParam(value = "valor_2", required = false) String valor_02);

    @PutMapping ("atualizar/{id}")
    public abstract ResponseEntity atualizar(@PathVariable UUID id, @RequestBody @Valid D dto);

    @DeleteMapping("excluir/{id}")
    public abstract ResponseEntity excluir(@PathVariable UUID id);

}