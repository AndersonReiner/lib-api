package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.AdministradorDto;
import com.anderson.lib_api.dto.LoginRequestDTO;
import com.anderson.lib_api.dto.ResponseDTO;
import com.anderson.lib_api.infra.security.TokenService;
import com.anderson.lib_api.models.Administrador;
import com.anderson.lib_api.repositories.AdministradorRepository;
import com.anderson.lib_api.services.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdministradorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AdministradorService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Administrador administrador = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), administrador.getSenha())) {
            String token = this.tokenService.generateToken(administrador);
            return ResponseEntity.ok(new ResponseDTO(administrador.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}

