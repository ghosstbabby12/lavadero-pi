package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.PrecioServicioVehiculoRequest;
import com.lavadero.lavadero_api.dto.response.PrecioServicioVehiculoResponse;

import java.util.List;

public interface PrecioServicioVehiculoService {
    PrecioServicioVehiculoResponse crear(Long servicioId, PrecioServicioVehiculoRequest request);
    List<PrecioServicioVehiculoResponse> listarPorServicio(Long servicioId);
    PrecioServicioVehiculoResponse actualizar(Long id, PrecioServicioVehiculoRequest request);
    void eliminar(Long id);
}
