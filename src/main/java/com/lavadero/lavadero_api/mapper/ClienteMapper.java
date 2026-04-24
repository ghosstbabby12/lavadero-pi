package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.ClienteResponse;
import com.lavadero.lavadero_api.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements EntityMapper<Cliente, ClienteResponse> {

    @Override
    public ClienteResponse toResponse(Cliente c) {
        return ClienteResponse.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .documento(c.getDocumento())
                .telefono(c.getTelefono())
                .email(c.getEmail())
                .build();
    }
}
