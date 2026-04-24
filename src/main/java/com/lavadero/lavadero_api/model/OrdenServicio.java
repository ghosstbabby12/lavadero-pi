package com.lavadero.lavadero_api.model;

import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "ordenes_servicio",
    indexes = {
        @Index(name = "idx_ordenes_vehiculo_id", columnList = "vehiculo_id"),
        @Index(name = "idx_ordenes_estado",      columnList = "estado")
    }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoOrden estado = EstadoOrden.REGISTRADA;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 30)
    @Builder.Default
    private List<DetalleOrden> detalles = new ArrayList<>();

    // @BatchSize evita N+1 al acceder a pagos de múltiples órdenes
    // No se incluye en JOIN FETCH junto con detalles para evitar MultipleBagFetchException
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @BatchSize(size = 30)
    @Builder.Default
    private List<Pago> pagos = new ArrayList<>();

    public void recalcularTotal() {
        this.total = detalles.stream()
                .map(DetalleOrden::getPrecioAplicado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
