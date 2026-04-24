package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPlaca(String placa);
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo> findByClienteId(Long clienteId);
}
