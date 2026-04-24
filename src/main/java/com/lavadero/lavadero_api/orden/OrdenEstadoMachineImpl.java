package com.lavadero.lavadero_api.orden;

import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class OrdenEstadoMachineImpl implements OrdenEstadoMachine {

    private static final Map<EstadoOrden, Set<EstadoOrden>> TRANSICIONES =
            new EnumMap<>(EstadoOrden.class);

    static {
        TRANSICIONES.put(EstadoOrden.REGISTRADA, EnumSet.of(EstadoOrden.EN_PROCESO, EstadoOrden.CANCELADA));
        TRANSICIONES.put(EstadoOrden.EN_PROCESO,  EnumSet.of(EstadoOrden.FINALIZADA, EstadoOrden.CANCELADA));
        TRANSICIONES.put(EstadoOrden.FINALIZADA,  EnumSet.of(EstadoOrden.ENTREGADA));
        TRANSICIONES.put(EstadoOrden.ENTREGADA,   EnumSet.noneOf(EstadoOrden.class));
        TRANSICIONES.put(EstadoOrden.CANCELADA,   EnumSet.noneOf(EstadoOrden.class));
    }

    @Override
    public void validarTransicion(EstadoOrden actual, EstadoOrden nuevo) {
        if (!TRANSICIONES.get(actual).contains(nuevo)) {
            throw new BusinessException("Transición inválida: " + actual + " → " + nuevo +
                    ". Permitidas: " + TRANSICIONES.get(actual));
        }
    }

    @Override
    public boolean esModificable(EstadoOrden estado) {
        return estado == EstadoOrden.REGISTRADA || estado == EstadoOrden.EN_PROCESO;
    }
}
