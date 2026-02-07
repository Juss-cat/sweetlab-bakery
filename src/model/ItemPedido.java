package model;

/**
 * Clase que representa un Item (Producto) dentro de un Pedido
 * Establece la relación entre Pedido y Producto
 */
public class ItemPedido {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private Promocion promocion;

    // Constructor
    public ItemPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.promocion = null;
    }

    // Métodos de cálculo
    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    public double getTotal() {
        double total = getSubtotal();
        if (promocion != null) {
            total = total - (total * promocion.getDescuento() / 100);
        }
        return total;
    }

    public double getDescuentoAplicado() {
        if (promocion != null) {
            return getSubtotal() * (promocion.getDescuento() / 100);
        }
        return 0;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", total=" + getTotal() +
                '}';
    }
}
