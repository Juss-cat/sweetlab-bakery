package model;

/**
 * Clase para representar items dentro de una factura
 */
public class ItemFactura {
    private int id;
    private int idFactura;
    private int idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ItemFactura(int id, int idFactura, int idProducto, String nombreProducto,
                      int cantidad, double precioUnitario) {
        this.id = id;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    // Getters
    public int getId() { return id; }
    public int getIdFactura() { return idFactura; }
    public int getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = cantidad * precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "ItemFactura{" +
                "id=" + id +
                ", idFactura=" + idFactura +
                ", idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}
