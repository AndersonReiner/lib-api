package com.anderson.lib_api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.lib_api.models.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {

    Optional<Administrador> findByNome(String nome);
    Optional<Administrador> findByEmail(String email);
    Optional<Administrador> findByCpf(String cpf);
    Optional<Administrador> findByUsuario(String usuario);
    
}
