package com.lavadero.lavadero_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "detalles_orden",
    indexes = {
        @Index(name = "idx_detalles_orden_id",    columnList = "orden_id"),
        @Index(name = "idx_detalles_servicio_id", columnList = "servicio_id")
    }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenServicio orden;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    // Precio capturado al momento de agregar el servicio a la orden
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioAplicado;

    @Column(length = 300)
    private String observaciones;
}
