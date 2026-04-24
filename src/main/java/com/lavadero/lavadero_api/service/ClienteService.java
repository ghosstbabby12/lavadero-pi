package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.ClienteRequest;
import com.lavadero.lavadero_api.dto.response.ClienteResponse;
import com.lavadero.lavadero_api.model.Cliente;

import java.util.List;

public interface ClienteService {
    ClienteResponse crear(ClienteRequest request);
    List<ClienteResponse> listar();
    ClienteResponse obtener(Long id);
    ClienteResponse actualizar(Long id, ClienteRequest request);
    void eliminar(Long id);
    /** Uso interno entre servicios — devuelve la entidad para evitar doble consulta. */
    Cliente buscarPorId(Long id);
}
