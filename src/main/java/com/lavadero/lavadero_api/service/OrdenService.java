package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.CambioEstadoRequest;
import com.lavadero.lavadero_api.dto.request.DetalleOrdenRequest;
import com.lavadero.lavadero_api.dto.request.OrdenRequest;
import com.lavadero.lavadero_api.dto.response.OrdenResponse;
import com.lavadero.lavadero_api.model.OrdenServicio;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;

import java.util.List;

public interface OrdenService {
    OrdenResponse crear(OrdenRequest request);
    List<OrdenResponse> listar(EstadoOrden estado);
    OrdenResponse obtenerDetalle(Long id);
    OrdenResponse cambiarEstado(Long id, CambioEstadoRequest request);
    OrdenResponse agregarDetalle(Long ordenId, DetalleOrdenRequest request);
    OrdenResponse eliminarDetalle(Long ordenId, Long detalleId);
    void cancelar(Long id);
    OrdenServicio buscarPorId(Long id);
}
