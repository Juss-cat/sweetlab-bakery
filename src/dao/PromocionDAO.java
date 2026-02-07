package dao;

import database.ConexionBD;
import model.Promocion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Promocion
 * Maneja todas las operaciones de base de datos para promociones
 */
public class PromocionDAO {

    /**
     * Inserta una nueva promoción en la base de datos
     */
    public void insertar(Promocion promocion) {
        String sql = "INSERT INTO promociones (nombre, descuento, fecha_inicio, fecha_fin, activa) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, promocion.getNombre());
            pstmt.setDouble(2, promocion.getDescuento());
            pstmt.setDate(3, Date.valueOf(promocion.getFechaInicio()));
            pstmt.setDate(4, Date.valueOf(promocion.getFechaFin()));
            pstmt.setBoolean(5, true);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        promocion.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar promoción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una promoción por su ID
     */
    public Promocion obtenerPorId(int id) {
        String sql = "SELECT * FROM promociones WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAPromocion(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener promoción: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Obtiene todas las promociones
     */
    public List<Promocion> obtenerTodas() {
        List<Promocion> promociones = new ArrayList<>();
        String sql = "SELECT * FROM promociones";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                promociones.add(mapearResultSetAPromocion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las promociones: " + e.getMessage());
            e.printStackTrace();
        }
        
        return promociones;
    }

    /**
     * Obtiene las promociones activas
     */
    public List<Promocion> obtenerActivas() {
        List<Promocion> promociones = new ArrayList<>();
        String sql = "SELECT * FROM promociones WHERE activa = 1 AND fecha_fin >= DATE('now')";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                promociones.add(mapearResultSetAPromocion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener promociones activas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return promociones;
    }

    /**
     * Actualiza una promoción existente
     */
    public void actualizar(Promocion promocion) {
        String sql = "UPDATE promociones SET nombre = ?, descuento = ?, fecha_inicio = ?, fecha_fin = ?, activa = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, promocion.getNombre());
            pstmt.setDouble(2, promocion.getDescuento());
            pstmt.setDate(3, Date.valueOf(promocion.getFechaInicio()));
            pstmt.setDate(4, Date.valueOf(promocion.getFechaFin()));
            pstmt.setBoolean(5, true);
            pstmt.setInt(6, promocion.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar promoción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina una promoción de la base de datos
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM promociones WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar promoción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Promocion
     */
    private Promocion mapearResultSetAPromocion(ResultSet rs) throws SQLException {
        return new Promocion(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("descuento"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getDate("fecha_fin").toLocalDate()
        );
    }
}
