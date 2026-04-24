package com.lavadero.lavadero_api.service;

import com.lavadero.lavadero_api.dto.request.PagoRequest;
import com.lavadero.lavadero_api.dto.response.PagoResponse;

import java.util.List;

public interface PagoService {
    PagoResponse registrar(Long ordenId, PagoRequest request);
    List<PagoResponse> listarPorOrden(Long ordenId);
}
