package com.lavadero.lavadero_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private String email;
}
