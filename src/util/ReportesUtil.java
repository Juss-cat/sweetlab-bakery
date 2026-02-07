package util;

import model.*;
import dao.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase de utilidades para reportes y estadísticas de la tienda
 */
public class ReportesUtil {
    
    private HistorialVentaDAO historialDAO;
    private FacturaDAO facturaDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    
    public ReportesUtil() {
        this.historialDAO = new HistorialVentaDAO();
        this.facturaDAO = new FacturaDAO();
        this.productoDAO = new ProductoDAO();
        this.clienteDAO = new ClienteDAO();
    }
    
    /**
     * Obtiene el reporte de ventas diarias
     */
    public double obtenerVentasDiarias(LocalDateTime fecha) {
        LocalDateTime inicio = fecha.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = fecha.withHour(23).withMinute(59).withSecond(59);
        return historialDAO.obtenerVentasTotalPorPeriodo(inicio, fin);
    }
    
    /**
     * Obtiene el reporte de ventas mensuales
     */
    public double obtenerVentasMensuales(YearMonth mes) {
        LocalDateTime inicio = mes.atDay(1).atStartOfDay();
        LocalDateTime fin = mes.atEndOfMonth().atTime(23, 59, 59);
        return historialDAO.obtenerVentasTotalPorPeriodo(inicio, fin);
    }
    
    /**
     * Obtiene el reporte de productos más vendidos
     */
    public Map<String, Integer> obtenerProductosMasVendidos(int limite) {
        Map<String, Integer> productos = new HashMap<>();
        List<HistorialVenta> historial = historialDAO.obtenerTodos();
        
        // Aquí idealmente iteraríamos por los items del pedido
        // Por ahora retornamos un mapa vacío como placeholder
        
        return productos;
    }
    
    /**
     * Obtiene el reporte de clientes más activos
     */
    public Map<String, Integer> obtenerClientesMasActivos(int limite) {
        Map<String, Integer> clientes = new HashMap<>();
        List<HistorialVenta> historial = historialDAO.obtenerTodos();
        
        for (HistorialVenta v : historial) {
            Cliente cliente = clienteDAO.obtenerPorId(v.getIdCliente());
            if (cliente != null) {
                String nombre = cliente.getNombre();
                clientes.put(nombre, clientes.getOrDefault(nombre, 0) + 1);
            }
        }
        
        return clientes;
    }
    
    /**
     * Calcula el promedio de venta por transacción
     */
    public double calcularPromediaVenta(LocalDateTime inicio, LocalDateTime fin) {
        List<HistorialVenta> historial = historialDAO.obtenerPorFecha(inicio, fin);
        if (historial.isEmpty()) return 0.0;
        
        double total = historial.stream().mapToDouble(HistorialVenta::getMontoTotal).sum();
        return total / historial.size();
    }
    
    /**
     * Obtiene el total de descuentos aplicados en un período
     */
    public double obtenerDescuentosPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        List<HistorialVenta> historial = historialDAO.obtenerPorFecha(inicio, fin);
        return historial.stream().mapToDouble(HistorialVenta::getDescuentoAplicado).sum();
    }
    
    /**
     * Obtiene estadísticas generales del negocio
     */
    public Map<String, Object> obtenerEstadisticasGenerales() {
        Map<String, Object> stats = new HashMap<>();
        
        List<HistorialVenta> ventas = historialDAO.obtenerTodos();
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        List<Producto> productos = productoDAO.obtenerTodos();
        List<Factura> facturas = facturaDAO.obtenerTodas();
        
        stats.put("totalVentas", ventas.size());
        stats.put("totalClientes", clientes.size());
        stats.put("totalProductos", productos.size());
        stats.put("totalFacturas", facturas.size());
        stats.put("ventasTotalMonto", ventas.stream().mapToDouble(HistorialVenta::getMontoTotal).sum());
        stats.put("descuentosTotales", ventas.stream().mapToDouble(HistorialVenta::getDescuentoAplicado).sum());
        
        return stats;
    }
    
    /**
     * Obtiene productos con bajo stock
     */
    public List<Producto> obtenerProductosBajoStock(int stockMinimo) {
        List<Producto> productos = productoDAO.obtenerTodos();
        return productos.stream()
                .filter(p -> p.getStock() < stockMinimo)
                .toList();
    }
}
