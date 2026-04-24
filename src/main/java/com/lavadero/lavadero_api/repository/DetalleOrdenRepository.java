package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

    List<DetalleOrden> findByOrdenId(Long ordenId);

    // Conteo eficiente sin cargar la colección completa
    boolean existsByOrdenId(Long ordenId);
}
