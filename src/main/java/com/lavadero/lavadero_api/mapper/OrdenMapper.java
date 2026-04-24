package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.OrdenResponse;
import com.lavadero.lavadero_api.model.OrdenServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper compuesto: delega el mapeo de sub-colecciones a
 * DetalleOrdenMapper y PagoMapper en lugar de duplicar la lógica.
 */
@Component
@RequiredArgsConstructor
public class OrdenMapper implements EntityMapper<OrdenServicio, OrdenResponse> {

    private final DetalleOrdenMapper detalleMapper;
    private final PagoMapper pagoMapper;

    @Override
    public OrdenResponse toResponse(OrdenServicio o) {
        return OrdenResponse.builder()
                .id(o.getId())
                .fechaCreacion(o.getFechaCreacion())
                .fechaActualizacion(o.getFechaActualizacion())
                .estado(o.getEstado())
                .observaciones(o.getObservaciones())
                .total(o.getTotal())
                .vehiculoId(o.getVehiculo().getId())
                .vehiculoPlaca(o.getVehiculo().getPlaca())
                .clienteNombreCompleto(
                        o.getVehiculo().getCliente().getNombre() + " " +
                        o.getVehiculo().getCliente().getApellido())
                .detalles(o.getDetalles().stream().map(detalleMapper::toResponse).toList())
                .pagos(o.getPagos().stream().map(pagoMapper::toResponse).toList())
                .build();
    }
}
