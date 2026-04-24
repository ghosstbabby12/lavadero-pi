package com.lavadero.lavadero_api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrdenRequest {

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @Size(max = 500)
    private String observaciones;
}
