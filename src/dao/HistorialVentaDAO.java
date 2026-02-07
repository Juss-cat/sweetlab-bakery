package dao;

import model.HistorialVenta;
import database.ConexionBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar el Historial de Ventas en la base de datos
 */
public class HistorialVentaDAO {
    
    public boolean crear(HistorialVenta historial) {
        String sql = "INSERT INTO historial_venta (id_pedido, id_cliente, fecha_venta, monto_total, " +
                     "estado, metodo_pago, descuento_aplicado, notas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, historial.getIdPedido());
            pstmt.setInt(2, historial.getIdCliente());
            pstmt.setString(3, historial.getFechaVenta().toString());
            pstmt.setDouble(4, historial.getMontoTotal());
            pstmt.setString(5, historial.getEstado());
            pstmt.setString(6, historial.getMetodoPago());
            pstmt.setDouble(7, historial.getDescuentoAplicado());
            pstmt.setString(8, historial.getNotas());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear historial: " + e.getMessage());
            return false;
        }
    }

    public HistorialVenta obtenerPorId(int id) {
        String sql = "SELECT * FROM historial_venta WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearHistorial(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
        }
        return null;
    }

    public List<HistorialVenta> obtenerTodos() {
        List<HistorialVenta> historial = new ArrayList<>();
        String sql = "SELECT * FROM historial_venta ORDER BY fecha_venta DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                historial.add(mapearHistorial(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
        }
        return historial;
    }

    public List<HistorialVenta> obtenerPorCliente(int idCliente) {
        List<HistorialVenta> historial = new ArrayList<>();
        String sql = "SELECT * FROM historial_venta WHERE id_cliente = ? ORDER BY fecha_venta DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                historial.add(mapearHistorial(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial del cliente: " + e.getMessage());
        }
        return historial;
    }

    public List<HistorialVenta> obtenerPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<HistorialVenta> historial = new ArrayList<>();
        String sql = "SELECT * FROM historial_venta WHERE fecha_venta BETWEEN ? AND ? ORDER BY fecha_venta DESC";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fechaInicio.toString());
            pstmt.setString(2, fechaFin.toString());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                historial.add(mapearHistorial(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial por fecha: " + e.getMessage());
        }
        return historial;
    }

    public boolean actualizar(HistorialVenta historial) {
        String sql = "UPDATE historial_venta SET id_pedido = ?, id_cliente = ?, fecha_venta = ?, " +
                     "monto_total = ?, estado = ?, metodo_pago = ?, descuento_aplicado = ?, notas = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, historial.getIdPedido());
            pstmt.setInt(2, historial.getIdCliente());
            pstmt.setString(3, historial.getFechaVenta().toString());
            pstmt.setDouble(4, historial.getMontoTotal());
            pstmt.setString(5, historial.getEstado());
            pstmt.setString(6, historial.getMetodoPago());
            pstmt.setDouble(7, historial.getDescuentoAplicado());
            pstmt.setString(8, historial.getNotas());
            pstmt.setInt(9, historial.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar historial: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM historial_venta WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar historial: " + e.getMessage());
            return false;
        }
    }

    public double obtenerVentasTotalPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        String sql = "SELECT SUM(monto_total) as total FROM historial_venta WHERE fecha_venta BETWEEN ? AND ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, inicio.toString());
            pstmt.setString(2, fin.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de ventas: " + e.getMessage());
        }
        return 0.0;
    }

    private HistorialVenta mapearHistorial(ResultSet rs) throws SQLException {
        return new HistorialVenta(
            rs.getInt("id"),
            rs.getInt("id_pedido"),
            rs.getInt("id_cliente"),
            LocalDateTime.parse(rs.getString("fecha_venta")),
            rs.getDouble("monto_total"),
            rs.getString("estado"),
            rs.getString("metodo_pago"),
            rs.getDouble("descuento_aplicado"),
            rs.getString("notas")
        );
    }
}
