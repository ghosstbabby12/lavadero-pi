package com.lavadero.lavadero_api.precio;

import com.lavadero.lavadero_api.model.Servicio;
import com.lavadero.lavadero_api.model.Vehiculo;

import java.math.BigDecimal;

/**
 * Estrategia para determinar el precio que se aplica al agregar
 * un servicio a una orden. Permite extender con nuevas reglas
 * (descuentos, promociones, recargos) sin modificar OrdenServiceImpl.
 */
public interface PrecioResolutionStrategy {
    BigDecimal resolver(Servicio servicio, Vehiculo vehiculo);
}
