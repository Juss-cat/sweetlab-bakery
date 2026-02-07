package model;

/**
 * Clase que representa un Producto de la pastelería Sweet Lab
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String tipo;
    private String descripcion;
    private int stock;
    private String imagenPath;
    private boolean esNuevo;
    private boolean esMasVendido;

    // Constructor
    public Producto(int id, String nombre, double precio, String tipo, String descripcion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.esNuevo = false;
        this.esMasVendido = false;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public boolean isEsMasVendido() {
        return esMasVendido;
    }

    public void setEsMasVendido(boolean esMasVendido) {
        this.esMasVendido = esMasVendido;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", stock=" + stock +
                '}';
    }
}
