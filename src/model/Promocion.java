package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una Promoción o Descuento
 */
public class Promocion {
    private int id;
    private String nombre;
    private double descuento; // Porcentaje (ej: 10 para 10%)
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Producto> productosAplicables;
    private boolean activa;

    // Constructor
    public Promocion(int id, String nombre, double descuento, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.productosAplicables = new ArrayList<>();
        this.activa = true;
    }

    // Métodos
    public void agregarProducto(Producto producto) {
        if (!productosAplicables.contains(producto)) {
            productosAplicables.add(producto);
        }
    }

    public void removerProducto(Producto producto) {
        productosAplicables.remove(producto);
    }

    public boolean esValida() {
        LocalDate hoy = LocalDate.now();
        return activa && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    public double calcularDescuento(double precio) {
        return precio * (descuento / 100);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Producto> getProductosAplicables() {
        return productosAplicables;
    }

    public void setProductosAplicables(List<Producto> productosAplicables) {
        this.productosAplicables = productosAplicables;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Promocion{" +
                "nombre='" + nombre + '\'' +
                ", descuento=" + descuento + "%" +
                ", activa=" + esValida() +
                '}';
    }
}
