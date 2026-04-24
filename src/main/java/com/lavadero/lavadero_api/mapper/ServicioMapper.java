package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.ServicioResponse;
import com.lavadero.lavadero_api.model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper implements EntityMapper<Servicio, ServicioResponse> {

    @Override
    public ServicioResponse toResponse(Servicio s) {
        return ServicioResponse.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .descripcion(s.getDescripcion())
                .precioBase(s.getPrecioBase())
                .activo(s.getActivo())
                .build();
    }
}
