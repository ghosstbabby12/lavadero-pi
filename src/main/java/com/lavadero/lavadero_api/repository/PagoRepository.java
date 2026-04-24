package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByOrdenId(Long ordenId);

    // SUM directo en BD — evita cargar todos los pagos solo para sumarlos
    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p WHERE p.orden.id = :ordenId")
    BigDecimal sumMontoByOrdenId(@Param("ordenId") Long ordenId);
}
