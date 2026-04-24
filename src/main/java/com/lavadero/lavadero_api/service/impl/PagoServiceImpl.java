package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.PagoRequest;
import com.lavadero.lavadero_api.dto.response.PagoResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.mapper.PagoMapper;
import com.lavadero.lavadero_api.model.OrdenServicio;
import com.lavadero.lavadero_api.model.Pago;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import com.lavadero.lavadero_api.repository.DetalleOrdenRepository;
import com.lavadero.lavadero_api.repository.PagoRepository;
import com.lavadero.lavadero_api.service.OrdenService;
import com.lavadero.lavadero_api.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final DetalleOrdenRepository detalleOrdenRepository;
    private final OrdenService ordenService;       // composición: delega búsqueda de orden
    private final PagoMapper pagoMapper;

    @Override
    @Transactional
    public PagoResponse registrar(Long ordenId, PagoRequest request) {
        OrdenServicio orden = ordenService.buscarPorId(ordenId);

        if (orden.getEstado() == EstadoOrden.CANCELADA) {
            throw new BusinessException("No se puede registrar pago en una orden cancelada");
        }
        if (orden.getEstado() == EstadoOrden.REGISTRADA) {
            throw new BusinessException("La orden debe estar en proceso o finalizada para registrar pagos");
        }
        if (!detalleOrdenRepository.existsByOrdenId(ordenId)) {
            throw new BusinessException("La orden no tiene servicios registrados");
        }

        // SUM directo en BD — sin traer todos los pagos a memoria
        BigDecimal totalPagado = pagoRepository.sumMontoByOrdenId(ordenId);
        BigDecimal pendiente = orden.getTotal().subtract(totalPagado);

        if (request.getMonto().compareTo(pendiente) > 0) {
            throw new BusinessException(
                    "El monto (" + request.getMonto() + ") supera el saldo pendiente (" + pendiente + ")");
        }

        Pago pago = Pago.builder()
                .orden(orden)
                .monto(request.getMonto())
                .metodoPago(request.getMetodoPago())
                .referencia(request.getReferencia())
                .build();
        return pagoMapper.toResponse(pagoRepository.save(pago));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponse> listarPorOrden(Long ordenId) {
        ordenService.buscarPorId(ordenId);
        return pagoRepository.findByOrdenId(ordenId).stream().map(pagoMapper::toResponse).toList();
    }
}
