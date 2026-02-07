package com.sweetlab.service;

import com.sweetlab.entity.Pedido;
import com.sweetlab.entity.ItemPedido;
import com.sweetlab.repository.PedidoRepository;
import com.sweetlab.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obtenerPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> obtenerPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return pedidoRepository.findByFechaPedidoBetween(inicio, fin);
    }

    public Pedido crear(Pedido pedido) {
        // Calcular total
        Double totalCalculado = 0.0;
        if (pedido.getItems() != null) {
            for (ItemPedido item : pedido.getItems()) {
                item.calcularSubtotal();
                if (item.getSubtotal() != null) {
                    totalCalculado += item.getSubtotal();
                }
            }
        }
        pedido.setTotal(totalCalculado);
        pedido.setEstado("PENDIENTE");
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        return pedidoRepository.findById(id).map(pedido -> {
            // Recalcular total
            Double totalCalculado = 0.0;
            if (pedidoActualizado.getItems() != null && !pedidoActualizado.getItems().isEmpty()) {
                for (ItemPedido item : pedidoActualizado.getItems()) {
                    item.calcularSubtotal();
                    totalCalculado += item.getSubtotal();
                }
                pedido.setItems(pedidoActualizado.getItems());
            }
            pedido.setTotal(totalCalculado);
            
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(nuevoEstado);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
