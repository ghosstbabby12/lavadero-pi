package com.lavadero.lavadero_api.dto.response;

import com.lavadero.lavadero_api.model.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponse {
    private Long id;
    private Long ordenId;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private LocalDateTime fechaPago;
    private String referencia;
}
