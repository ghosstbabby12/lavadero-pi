package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    boolean existsByNombre(String nombre);
    List<Servicio> findByActivoTrue();
}
