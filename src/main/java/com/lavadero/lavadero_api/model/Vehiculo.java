package com.lavadero.lavadero_api.model;

import com.lavadero.lavadero_api.model.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "vehiculos",
    indexes = {
        @Index(name = "idx_vehiculos_placa",      columnList = "placa"),
        @Index(name = "idx_vehiculos_cliente_id",  columnList = "cliente_id")
    }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String placa;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(length = 30)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipoVehiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Cascade removido: eliminar un vehículo NO debe eliminar órdenes históricas
    @OneToMany(mappedBy = "vehiculo")
    @BatchSize(size = 20)
    @Builder.Default
    private List<OrdenServicio> ordenes = new ArrayList<>();
}
