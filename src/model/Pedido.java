package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Pedido realizado por un cliente
 */
public class Pedido {
    public enum EstadoPedido {
        PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO
    }

    private int id;
    private Cliente cliente;
    private List<ItemPedido> items;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;
    private EstadoPedido estado;
    private double total;

    // Constructor
    public Pedido(int id, Cliente cliente, LocalDate fechaPedido) {
        this.id = id;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.fechaPedido = fechaPedido;
        this.estado = EstadoPedido.PENDIENTE;
        this.total = 0;
    }

    // Métodos
    public void agregarItem(ItemPedido item) {
        items.add(item);
        calcularTotal();
    }

    public void removerItem(ItemPedido item) {
        items.remove(item);
        calcularTotal();
    }

    public void removerItem(int indice) {
        if (indice >= 0 && indice < items.size()) {
            items.remove(indice);
            calcularTotal();
        }
    }

    public void calcularTotal() {
        total = 0;
        for (ItemPedido item : items) {
            total += item.getTotal();
        }
    }

    public int getCantidadItems() {
        return items.size();
    }

    public double getTotalDescuentos() {
        double descuentos = 0;
        for (ItemPedido item : items) {
            descuentos += item.getDescuentoAplicado();
        }
        return descuentos;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
        calcularTotal();
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", items=" + items.size() +
                ", total=" + total +
                ", estado=" + estado +
                '}';
    }
}
