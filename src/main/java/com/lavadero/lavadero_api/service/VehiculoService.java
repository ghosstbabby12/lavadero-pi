package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.VehiculoRequest;
import com.lavadero.lavadero_api.dto.response.VehiculoResponse;
import com.lavadero.lavadero_api.model.Vehiculo;

import java.util.List;

public interface VehiculoService {
    VehiculoResponse crear(VehiculoRequest request);
    List<VehiculoResponse> listar();
    List<VehiculoResponse> listarPorCliente(Long clienteId);
    VehiculoResponse obtener(Long id);
    VehiculoResponse actualizar(Long id, VehiculoRequest request);
    Vehiculo buscarPorId(Long id);
}
