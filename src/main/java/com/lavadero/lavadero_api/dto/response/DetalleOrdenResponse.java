package com.lavadero.lavadero_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleOrdenResponse {
    private Long id;
    private Long servicioId;
    private String servicioNombre;
    private BigDecimal precioAplicado;
    private String observaciones;
}
