package com.anderson.lib_api.controllers;

import com.anderson.lib_api.dto.AdministradorDto;
import com.anderson.lib_api.dto.LoginRequestDTO;
import com.anderson.lib_api.dto.ResponseDTO;
import com.anderson.lib_api.infra.security.TokenService;
import com.anderson.lib_api.models.Administrador;
import com.anderson.lib_api.repositories.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdministradorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Administrador user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AdministradorDto body){
        Optional<Administrador> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            Administrador newUser = new Administrador();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setEmail(body.email());
            newUser.setNome(body.nome());
            newUser.setDataNasc(body.dataNasc());
            newUser.setUsuario(body.usuario());
            newUser.setCpf(body.cpf());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

}

