package com.lavadero.lavadero_api.orden;

import com.lavadero.lavadero_api.model.enums.EstadoOrden;

/**
 * Contrato para la máquina de estados de una OrdenServicio.
 * Se delega a esta interfaz toda la lógica de transiciones válidas,
 * manteniendo OrdenServiceImpl libre de esa responsabilidad.
 */
public interface OrdenEstadoMachine {

    /** Lanza BusinessException si la transición actual → nuevo no está permitida. */
    void validarTransicion(EstadoOrden actual, EstadoOrden nuevo);

    /** Indica si la orden acepta agregar o quitar servicios en el estado dado. */
    boolean esModificable(EstadoOrden estado);
}
