package com.lavadero.lavadero_api.dto.request;

import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehiculoRequest {

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 15)
    private String placa;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50)
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50)
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "Año inválido")
    @Max(value = 2100, message = "Año inválido")
    private Integer anio;

    @Size(max = 30)
    private String color;

    @NotNull(message = "El tipo de vehículo es obligatorio")
    private TipoVehiculo tipoVehiculo;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
}
