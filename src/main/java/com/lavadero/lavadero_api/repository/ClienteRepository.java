package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    Optional<Cliente> findByDocumento(String documento);
}
