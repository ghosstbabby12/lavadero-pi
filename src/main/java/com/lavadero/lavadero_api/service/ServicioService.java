package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.ServicioRequest;
import com.lavadero.lavadero_api.dto.response.ServicioResponse;
import com.lavadero.lavadero_api.model.Servicio;

import java.util.List;

public interface ServicioService {
    ServicioResponse crear(ServicioRequest request);
    List<ServicioResponse> listar(boolean soloActivos);
    ServicioResponse obtener(Long id);
    ServicioResponse actualizar(Long id, ServicioRequest request);
    Servicio buscarPorId(Long id);
}
