package com.lavadero.lavadero_api.model;

import com.lavadero.lavadero_api.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "pagos",
    indexes = @Index(name = "idx_pagos_orden_id", columnList = "orden_id")
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenServicio orden;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPago;

    @Column(length = 100)
    private String referencia;
}
