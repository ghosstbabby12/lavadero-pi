package com.lavadero.lavadero_api.dto.response;

import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehiculoResponse {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private TipoVehiculo tipoVehiculo;
    private Long clienteId;
    private String clienteNombreCompleto;
}
