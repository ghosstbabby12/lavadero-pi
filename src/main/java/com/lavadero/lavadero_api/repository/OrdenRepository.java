package com.lavadero.lavadero_api.repository;

import com.lavadero.lavadero_api.model.OrdenServicio;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdenRepository extends JpaRepository<OrdenServicio, Long> {

    // JOIN FETCH vehiculo+cliente para evitar N+1 en el listado
    @Query("SELECT DISTINCT o FROM OrdenServicio o " +
           "JOIN FETCH o.vehiculo v " +
           "JOIN FETCH v.cliente")
    List<OrdenServicio> findAllWithVehiculo();

    @Query("SELECT DISTINCT o FROM OrdenServicio o " +
           "JOIN FETCH o.vehiculo v " +
           "JOIN FETCH v.cliente " +
           "WHERE o.estado = :estado")
    List<OrdenServicio> findByEstadoWithVehiculo(@Param("estado") EstadoOrden estado);

    List<OrdenServicio> findByVehiculoId(Long vehiculoId);

    // Solo fetcha detalles+servicio — NO pagos en el mismo query para evitar
    // MultipleBagFetchException (dos @OneToMany List en una sola consulta SQL)
    @Query("SELECT DISTINCT o FROM OrdenServicio o " +
           "JOIN FETCH o.vehiculo v " +
           "JOIN FETCH v.cliente " +
           "LEFT JOIN FETCH o.detalles d " +
           "LEFT JOIN FETCH d.servicio " +
           "WHERE o.id = :id")
    Optional<OrdenServicio> findByIdWithDetalles(@Param("id") Long id);
}
