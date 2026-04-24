package com.lavadero.lavadero_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServicioResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioBase;
    private Boolean activo;
}
