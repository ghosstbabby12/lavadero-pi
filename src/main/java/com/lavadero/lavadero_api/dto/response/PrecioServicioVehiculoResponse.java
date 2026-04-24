package com.lavadero.lavadero_api.dto.response;

import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PrecioServicioVehiculoResponse {
    private Long id;
    private Long servicioId;
    private String servicioNombre;
    private TipoVehiculo tipoVehiculo;
    private BigDecimal precio;
}
