package com.lavadero.lavadero_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleOrdenRequest {

    @NotNull(message = "El servicio es obligatorio")
    private Long servicioId;

    // Si no se envía, se toma el precioBase del servicio
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
    private BigDecimal precioAplicado;

    @Size(max = 300)
    private String observaciones;
}
