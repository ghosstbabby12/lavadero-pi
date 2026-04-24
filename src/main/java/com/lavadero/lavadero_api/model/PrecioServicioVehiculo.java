package com.lavadero.lavadero_api.model;

import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "precios_servicio_vehiculo",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_precio_servicio_tipo",
        columnNames = {"servicio_id", "tipo_vehiculo"}
    )
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrecioServicioVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo", nullable = false)
    private TipoVehiculo tipoVehiculo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}
