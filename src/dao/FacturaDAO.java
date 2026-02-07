package dao;

import model.Factura;
import model.ItemFactura;
import database.ConexionBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar Facturas en la base de datos
 */
public class FacturaDAO {
    
    public boolean crear(Factura factura) {
        String sql = "INSERT INTO factura (id_pedido, numero_factura, id_cliente, fecha_emision, " +
                     "subtotal, impuesto, descuento, total, estado, metodo_pago) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, factura.getIdPedido());
            pstmt.setInt(2, factura.getNumeroFactura());
            pstmt.setInt(3, factura.getIdCliente());
            pstmt.setString(4, factura.getFechaEmision().toString());
            pstmt.setDouble(5, factura.getSubtotal());
            pstmt.setDouble(6, factura.getImpuesto());
            pstmt.setDouble(7, factura.getDescuento());
            pstmt.setDouble(8, factura.getTotal());
            pstmt.setString(9, factura.getEstado());
            pstmt.setString(10, factura.getMetodoPago());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        factura.setId(id);
                        
                        // Insertar items
                        for (ItemFactura item : factura.getItems()) {
                            crearItemFactura(item, id, conn);
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear factura: " + e.getMessage());
        }
        return false;
    }

    private void crearItemFactura(ItemFactura item, int idFactura, Connection conn) {
        String sql = "INSERT INTO item_factura (id_factura, id_producto, nombre_producto, cantidad, precio_unitario) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFactura);
            pstmt.setInt(2, item.getIdProducto());
            pstmt.setString(3, item.getNombreProducto());
            pstmt.setInt(4, item.getCantidad());
            pstmt.setDouble(5, item.getPrecioUnitario());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al crear item de factura: " + e.getMessage());
        }
    }

    public Factura obtenerPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Factura factura = mapearFactura(rs);
                factura.setItems(obtenerItemsPorFactura(id));
                return factura;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener factura: " + e.getMessage());
        }
        return null;
    }

    public Factura obtenerPorNumero(int numeroFactura) {
        String sql = "SELECT * FROM factura WHERE numero_factura = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, numeroFactura);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Factura factura = mapearFactura(rs);
                factura.setItems(obtenerItemsPorFactura(factura.getId()));
                return factura;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener factura: " + e.getMessage());
        }
        return null;
    }

    public List<Factura> obtenerTodas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM factura ORDER BY fecha_emision DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Factura factura = mapearFactura(rs);
                factura.setItems(obtenerItemsPorFactura(factura.getId()));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener facturas: " + e.getMessage());
        }
        return facturas;
    }

    public List<Factura> obtenerPorCliente(int idCliente) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM factura WHERE id_cliente = ? ORDER BY fecha_emision DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Factura factura = mapearFactura(rs);
                factura.setItems(obtenerItemsPorFactura(factura.getId()));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener facturas del cliente: " + e.getMessage());
        }
        return facturas;
    }

    private List<ItemFactura> obtenerItemsPorFactura(int idFactura) {
        List<ItemFactura> items = new ArrayList<>();
        String sql = "SELECT * FROM item_factura WHERE id_factura = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idFactura);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ItemFactura item = new ItemFactura(
                    rs.getInt("id"),
                    rs.getInt("id_factura"),
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener items de factura: " + e.getMessage());
        }
        return items;
    }

    public boolean actualizar(Factura factura) {
        String sql = "UPDATE factura SET id_pedido = ?, numero_factura = ?, id_cliente = ?, " +
                     "fecha_emision = ?, subtotal = ?, impuesto = ?, descuento = ?, total = ?, " +
                     "estado = ?, metodo_pago = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, factura.getIdPedido());
            pstmt.setInt(2, factura.getNumeroFactura());
            pstmt.setInt(3, factura.getIdCliente());
            pstmt.setString(4, factura.getFechaEmision().toString());
            pstmt.setDouble(5, factura.getSubtotal());
            pstmt.setDouble(6, factura.getImpuesto());
            pstmt.setDouble(7, factura.getDescuento());
            pstmt.setDouble(8, factura.getTotal());
            pstmt.setString(9, factura.getEstado());
            pstmt.setString(10, factura.getMetodoPago());
            pstmt.setInt(11, factura.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar factura: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM factura WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    private Factura mapearFactura(ResultSet rs) throws SQLException {
        return new Factura(
            rs.getInt("id"),
            rs.getInt("id_pedido"),
            rs.getInt("numero_factura"),
            rs.getInt("id_cliente"),
            LocalDateTime.parse(rs.getString("fecha_emision")),
            rs.getDouble("subtotal"),
            rs.getDouble("impuesto"),
            rs.getDouble("descuento"),
            rs.getDouble("total"),
            rs.getString("estado"),
            rs.getString("metodo_pago")
        );
    }
}
