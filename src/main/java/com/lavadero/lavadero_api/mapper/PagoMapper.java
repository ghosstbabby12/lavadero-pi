package com.lavadero.lavadero_api.mapper;

import com.lavadero.lavadero_api.dto.response.PagoResponse;
import com.lavadero.lavadero_api.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper implements EntityMapper<Pago, PagoResponse> {

    @Override
    public PagoResponse toResponse(Pago p) {
        return PagoResponse.builder()
                .id(p.getId())
                .ordenId(p.getOrden().getId())
                .monto(p.getMonto())
                .metodoPago(p.getMetodoPago())
                .fechaPago(p.getFechaPago())
                .referencia(p.getReferencia())
                .build();
    }
}
