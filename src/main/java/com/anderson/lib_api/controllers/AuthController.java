package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.AuthDto;
import com.anderson.lib_api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
public class AuthController {

    @Autowired
    public AuthService service;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthDto dto){

        return service.login(dto);

    }

}

