package com.lavadero.lavadero_api.dto.response;

import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrdenResponse {
    private Long id;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private EstadoOrden estado;
    private String observaciones;
    private BigDecimal total;
    private Long vehiculoId;
    private String vehiculoPlaca;
    private String clienteNombreCompleto;
    private List<DetalleOrdenResponse> detalles;
    private List<PagoResponse> pagos;
}
