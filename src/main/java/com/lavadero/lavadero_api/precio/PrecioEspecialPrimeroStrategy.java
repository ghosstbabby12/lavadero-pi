package com.lavadero.lavadero_api.precio;

import com.lavadero.lavadero_api.model.Servicio;
import com.lavadero.lavadero_api.model.Vehiculo;
import com.lavadero.lavadero_api.repository.PrecioServicioVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementación de la estrategia de precio con la siguiente prioridad:
 *   1. Precio especial por tipo de vehículo (tabla precios_servicio_vehiculo)
 *   2. Precio base del servicio (fallback)
 *
 * Para aplicar promociones o recargos en el futuro, se puede crear otra
 * implementación de PrecioResolutionStrategy y cambiar el bean activo.
 */
@Component
@RequiredArgsConstructor
public class PrecioEspecialPrimeroStrategy implements PrecioResolutionStrategy {

    private final PrecioServicioVehiculoRepository precioRepository;

    @Override
    public BigDecimal resolver(Servicio servicio, Vehiculo vehiculo) {
        return precioRepository
                .findByServicioIdAndTipoVehiculo(servicio.getId(), vehiculo.getTipoVehiculo())
                .map(p -> p.getPrecio())
                .orElse(servicio.getPrecioBase());
    }
}
