package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.PrecioServicioVehiculo;
import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrecioServicioVehiculoRepository extends JpaRepository<PrecioServicioVehiculo, Long> {

    Optional<PrecioServicioVehiculo> findByServicioIdAndTipoVehiculo(Long servicioId, TipoVehiculo tipoVehiculo);

    List<PrecioServicioVehiculo> findByServicioId(Long servicioId);

    boolean existsByServicioIdAndTipoVehiculo(Long servicioId, TipoVehiculo tipoVehiculo);
}
