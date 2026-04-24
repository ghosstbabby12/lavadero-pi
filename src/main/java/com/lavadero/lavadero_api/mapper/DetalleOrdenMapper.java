package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.DetalleOrdenResponse;
import com.lavadero.lavadero_api.model.DetalleOrden;
import org.springframework.stereotype.Component;

@Component
public class DetalleOrdenMapper implements EntityMapper<DetalleOrden, DetalleOrdenResponse> {

    @Override
    public DetalleOrdenResponse toResponse(DetalleOrden d) {
        return DetalleOrdenResponse.builder()
                .id(d.getId())
                .servicioId(d.getServicio().getId())
                .servicioNombre(d.getServicio().getNombre())
                .precioAplicado(d.getPrecioAplicado())
                .observaciones(d.getObservaciones())
                .build();
    }
}
