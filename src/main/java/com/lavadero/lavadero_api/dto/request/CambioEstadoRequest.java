package com.lavadero.lavadero_api.dto.request;

import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambioEstadoRequest {

    @NotNull(message = "El estado es obligatorio")
    private EstadoOrden estado;
}
