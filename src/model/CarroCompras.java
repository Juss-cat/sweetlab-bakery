package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el Carrito de Compras de un cliente
 */
public class CarroCompras {
    private List<ItemPedido> items;
    private double total;

    // Constructor
    public CarroCompras() {
        this.items = new ArrayList<>();
        this.total = 0;
    }

    // Métodos
    public void agregarProducto(Producto producto, int cantidad) {
        // Verificar si el producto ya está en el carrito
        for (ItemPedido item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                calcularTotal();
                return;
            }
        }
        // Si no está, agregarlo como nuevo item
        ItemPedido nuevoItem = new ItemPedido(producto, cantidad);
        items.add(nuevoItem);
        calcularTotal();
    }

    public void removerProducto(int indiceProd) {
        if (indiceProd >= 0 && indiceProd < items.size()) {
            items.remove(indiceProd);
            calcularTotal();
        }
    }

    public void actualizarCantidad(int indice, int nuevaCantidad) {
        if (indice >= 0 && indice < items.size()) {
            if (nuevaCantidad <= 0) {
                removerProducto(indice);
            } else {
                items.get(indice).setCantidad(nuevaCantidad);
                calcularTotal();
            }
        }
    }

    public void aplicarPromocion(int indiceItem, Promocion promocion) {
        if (indiceItem >= 0 && indiceItem < items.size()) {
            items.get(indiceItem).setPromocion(promocion);
            calcularTotal();
        }
    }

    public void calcularTotal() {
        total = 0;
        for (ItemPedido item : items) {
            total += item.getTotal();
        }
    }

    public void vaciar() {
        items.clear();
        total = 0;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int getCantidadProductos() {
        int cantidad = 0;
        for (ItemPedido item : items) {
            cantidad += item.getCantidad();
        }
        return cantidad;
    }

    // Getters
    public List<ItemPedido> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CarroCompras{" +
                "items=" + items.size() +
                ", total=" + total +
                '}';
    }
}
