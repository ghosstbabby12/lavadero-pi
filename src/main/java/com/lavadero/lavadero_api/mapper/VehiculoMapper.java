package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.VehiculoResponse;
import com.lavadero.lavadero_api.model.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper implements EntityMapper<Vehiculo, VehiculoResponse> {

    @Override
    public VehiculoResponse toResponse(Vehiculo v) {
        return VehiculoResponse.builder()
                .id(v.getId())
                .placa(v.getPlaca())
                .marca(v.getMarca())
                .modelo(v.getModelo())
                .anio(v.getAnio())
                .color(v.getColor())
                .tipoVehiculo(v.getTipoVehiculo())
                .clienteId(v.getCliente().getId())
                .clienteNombreCompleto(v.getCliente().getNombre() + " " + v.getCliente().getApellido())
                .build();
    }
}
