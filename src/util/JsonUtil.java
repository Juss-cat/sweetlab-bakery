package util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Cliente;
import model.Pedido;
import model.Producto;
import model.Promocion;

/**
 * Utilidad para manejar operaciones JSON
 * Permite exportar datos a JSON y leer configuraciones
 */
public class JsonUtil {

    /**
     * Exporta una lista de productos a JSON
     */
    public static void exportarProductosJSON(List<Producto> productos, String nombreArchivo) {
        try {
            JSONArray jsonArray = new JSONArray();
            
            for (Producto p : productos) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", p.getId());
                jsonObj.put("nombre", p.getNombre());
                jsonObj.put("precio", p.getPrecio());
                jsonObj.put("tipo", p.getTipo());
                jsonObj.put("descripcion", p.getDescripcion());
                jsonObj.put("stock", p.getStock());
                jsonObj.put("esNuevo", p.isEsNuevo());
                jsonObj.put("esMasVendido", p.isEsMasVendido());
                
                jsonArray.put(jsonObj);
            }
            
            guardarJSON(jsonArray, nombreArchivo);
            System.out.println("✓ Productos exportados a: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("✗ Error al exportar productos: " + e.getMessage());
        }
    }

    /**
     * Exporta una lista de clientes a JSON
     */
    public static void exportarClientesJSON(List<Cliente> clientes, String nombreArchivo) {
        try {
            JSONArray jsonArray = new JSONArray();
            
            for (Cliente c : clientes) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", c.getId());
                jsonObj.put("nombre", c.getNombre());
                jsonObj.put("email", c.getEmail());
                jsonObj.put("telefono", c.getTelefono());
                jsonObj.put("direccion", c.getDireccion());
                
                jsonArray.put(jsonObj);
            }
            
            guardarJSON(jsonArray, nombreArchivo);
            System.out.println("✓ Clientes exportados a: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("✗ Error al exportar clientes: " + e.getMessage());
        }
    }

    /**
     * Exporta el historial de pedidos a JSON
     */
    public static void exportarPedidosJSON(List<Pedido> pedidos, String nombreArchivo) {
        try {
            JSONArray jsonArray = new JSONArray();
            
            for (Pedido p : pedidos) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", p.getId());
                jsonObj.put("clienteId", p.getCliente().getId());
                jsonObj.put("clienteNombre", p.getCliente().getNombre());
                jsonObj.put("total", p.getTotal());
                jsonObj.put("estado", p.getEstado().toString());
                jsonObj.put("fechaPedido", p.getFechaPedido().toString());
                jsonObj.put("cantidadItems", p.getCantidadItems());
                
                jsonArray.put(jsonObj);
            }
            
            guardarJSON(jsonArray, nombreArchivo);
            System.out.println("✓ Pedidos exportados a: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("✗ Error al exportar pedidos: " + e.getMessage());
        }
    }

    /**
     * Exporta promociones a JSON
     */
    public static void exportarPromocionesJSON(List<Promocion> promociones, String nombreArchivo) {
        try {
            JSONArray jsonArray = new JSONArray();
            
            for (Promocion promo : promociones) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", promo.getId());
                jsonObj.put("nombre", promo.getNombre());
                jsonObj.put("descuentoPorcentaje", promo.getDescuento());
                jsonObj.put("estado", promo.isActiva());
                jsonObj.put("fechaInicio", promo.getFechaInicio().toString());
                jsonObj.put("fechaFin", promo.getFechaFin().toString());
                
                jsonArray.put(jsonObj);
            }
            
            guardarJSON(jsonArray, nombreArchivo);
            System.out.println("✓ Promociones exportadas a: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("✗ Error al exportar promociones: " + e.getMessage());
        }
    }

    /**
     * Exporta un reporte general de la tienda
     */
    public static void exportarReporteJSON(
            List<Producto> productos, 
            List<Cliente> clientes, 
            List<Pedido> pedidos,
            String nombreArchivo) {
        try {
            JSONObject reporteObj = new JSONObject();
            
            // Información de la aplicación
            JSONObject appInfo = new JSONObject();
            appInfo.put("nombre", "Sweet Lab Bakery");
            appInfo.put("version", "3.0.0");
            appInfo.put("fecha_reporte", java.time.LocalDateTime.now().toString());
            reporteObj.put("aplicacion", appInfo);
            
            // Resumen
            JSONObject resumen = new JSONObject();
            resumen.put("total_productos", productos.size());
            resumen.put("total_clientes", clientes.size());
            resumen.put("total_pedidos", pedidos.size());
            double ingresoTotal = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
            resumen.put("ingreso_total", ingresoTotal);
            reporteObj.put("resumen", resumen);
            
            // Datos
            JSONArray prodArray = new JSONArray();
            for (Producto p : productos) {
                JSONObject pObj = new JSONObject();
                pObj.put("id", p.getId());
                pObj.put("nombre", p.getNombre());
                pObj.put("precio", p.getPrecio());
                prodArray.put(pObj);
            }
            reporteObj.put("productos", prodArray);
            
            JSONArray clientArray = new JSONArray();
            for (Cliente c : clientes) {
                JSONObject cObj = new JSONObject();
                cObj.put("id", c.getId());
                cObj.put("nombre", c.getNombre());
                clientArray.put(cObj);
            }
            reporteObj.put("clientes", clientArray);
            
            guardarJSON(reporteObj, nombreArchivo);
            System.out.println("✓ Reporte exportado a: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("✗ Error al exportar reporte: " + e.getMessage());
        }
    }

    /**
     * Lee la configuración desde config.json
     */
    public static JSONObject leerConfiguracion() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("config.json")));
            return new JSONObject(contenido);
        } catch (IOException e) {
            System.err.println("✗ Error al leer config.json: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lee datos iniciales desde datos_iniciales.json
     */
    public static JSONObject leerDatosIniciales() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("datos_iniciales.json")));
            return new JSONObject(contenido);
        } catch (IOException e) {
            System.err.println("✗ Error al leer datos_iniciales.json: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda un JSONObject o JSONArray en archivo
     */
    private static void guardarJSON(Object jsonObject, String nombreArchivo) throws IOException {
        try (FileWriter file = new FileWriter(nombreArchivo)) {
            if (jsonObject instanceof JSONArray) {
                file.write(((JSONArray) jsonObject).toString(2));
            } else if (jsonObject instanceof JSONObject) {
                file.write(((JSONObject) jsonObject).toString(2));
            }
            file.flush();
        }
    }
}
