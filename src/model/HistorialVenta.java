package model;

import java.time.LocalDateTime;

/**
 * Clase que representa el Historial de Ventas
 * Registra todas las transacciones realizadas
 */
public class HistorialVenta {
    private int id;
    private int idPedido;
    private int idCliente;
    private LocalDateTime fechaVenta;
    private double montoTotal;
    private String estado; // "Completado", "Cancelado", "Pendiente"
    private String metodoPago; // "Efectivo", "Tarjeta", "Transferencia"
    private double descuentoAplicado;
    private String notas;

    // Constructor completo
    public HistorialVenta(int id, int idPedido, int idCliente, LocalDateTime fechaVenta,
                         double montoTotal, String estado, String metodoPago,
                         double descuentoAplicado, String notas) {
        this.id = id;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.descuentoAplicado = descuentoAplicado;
        this.notas = notas;
    }

    // Constructor sin ID (para inserciones)
    public HistorialVenta(int idPedido, int idCliente, LocalDateTime fechaVenta,
                         double montoTotal, String estado, String metodoPago,
                         double descuentoAplicado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.descuentoAplicado = descuentoAplicado;
        this.notas = "";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(double descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    @Override
    public String toString() {
        return "HistorialVenta{" +
                "id=" + id +
                ", idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", fechaVenta=" + fechaVenta +
                ", montoTotal=" + montoTotal +
                ", estado='" + estado + '\'' +
                ", metodoPago='" + metodoPago + '\'' +
                ", descuentoAplicado=" + descuentoAplicado +
                '}';
    }
}
