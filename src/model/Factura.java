package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una Factura
 * Documento legal de una venta
 */
public class Factura {
    private int id;
    private int idPedido;
    private int numeroFactura; // Número correlativo
    private int idCliente;
    private LocalDateTime fechaEmision;
    private double subtotal;
    private double impuesto; // IVA u otro impuesto
    private double descuento;
    private double total;
    private String estado; // "Emitida", "Pagada", "Cancelada"
    private List<ItemFactura> items;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String observaciones;

    // Constructor completo
    public Factura(int id, int idPedido, int numeroFactura, int idCliente,
                   LocalDateTime fechaEmision, double subtotal, double impuesto,
                   double descuento, double total, String estado, String metodoPago) {
        this.id = id;
        this.idPedido = idPedido;
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = total;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.items = new ArrayList<>();
    }

    // Constructor sin ID
    public Factura(int numeroFactura, int idCliente, LocalDateTime fechaEmision,
                   double subtotal, double impuesto, double descuento) {
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = subtotal + impuesto - descuento;
        this.estado = "Emitida";
        this.items = new ArrayList<>();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(int numeroFactura) { this.numeroFactura = numeroFactura; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { 
        this.subtotal = subtotal;
        recalcularTotal();
    }

    public double getImpuesto() { return impuesto; }
    public void setImpuesto(double impuesto) { 
        this.impuesto = impuesto;
        recalcularTotal();
    }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { 
        this.descuento = descuento;
        recalcularTotal();
    }

    public double getTotal() { return total; }
    private void recalcularTotal() {
        this.total = subtotal + impuesto - descuento;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<ItemFactura> getItems() { return items; }
    public void setItems(List<ItemFactura> items) { this.items = items; }

    public void agregarItem(ItemFactura item) {
        this.items.add(item);
    }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", numeroFactura=" + numeroFactura +
                ", idCliente=" + idCliente +
                ", fechaEmision=" + fechaEmision +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
