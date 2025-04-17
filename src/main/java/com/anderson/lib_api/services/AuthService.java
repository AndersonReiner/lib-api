package com.anderson.lib_api.services;

import com.anderson.lib_api.dto.AuthDto;
import com.anderson.lib_api.models.Administrador;
import com.anderson.lib_api.repositories.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    public AdministradorRepository repository;


    public ResponseEntity login(AuthDto dto) {
        Optional<Administrador> userOpt = repository.findByUsuario(dto.usuario());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("USUÁRIO: " + dto.usuario() + " NÃO EXISTE!");
        }

        Administrador user = userOpt.get();
        if (!user.getSenha().equals(dto.senha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("SENHA INCORRETA!");
        }

        return ResponseEntity.ok("CREDENCIAIS AUTORIZADAS");
    }


}
