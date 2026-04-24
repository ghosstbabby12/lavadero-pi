package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.PrecioServicioVehiculoResponse;
import com.lavadero.lavadero_api.model.PrecioServicioVehiculo;
import org.springframework.stereotype.Component;

@Component
public class PrecioServicioVehiculoMapper implements EntityMapper<PrecioServicioVehiculo, PrecioServicioVehiculoResponse> {

    @Override
    public PrecioServicioVehiculoResponse toResponse(PrecioServicioVehiculo p) {
        return PrecioServicioVehiculoResponse.builder()
                .id(p.getId())
                .servicioId(p.getServicio().getId())
                .servicioNombre(p.getServicio().getNombre())
                .tipoVehiculo(p.getTipoVehiculo())
                .precio(p.getPrecio())
                .build();
    }
}
