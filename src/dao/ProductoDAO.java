package dao;

import database.ConexionBD;
import model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Producto
 * Maneja todas las operaciones de base de datos para productos
 */
public class ProductoDAO {

    /**
     * Inserta un nuevo producto en la base de datos
     */
    public void insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, tipo, descripcion, stock) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getTipo());
            pstmt.setString(4, producto.getDescripcion());
            pstmt.setInt(5, producto.getStock());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            // Obtener el ID generado
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene un producto por su ID
     */
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAProducto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Obtiene todos los productos
     */
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(mapearResultSetAProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productos;
    }

    /**
     * Obtiene productos por tipo
     */
    public List<Producto> obtenerPorTipo(String tipo) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE tipo = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearResultSetAProducto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return productos;
    }

    /**
     * Actualiza un producto existente
     */
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, tipo = ?, descripcion = ?, stock = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getTipo());
            pstmt.setString(4, producto.getDescripcion());
            pstmt.setInt(5, producto.getStock());
            pstmt.setInt(6, producto.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina un producto de la base de datos
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Producto
     */
    private Producto mapearResultSetAProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("tipo"),
                rs.getString("descripcion"),
                rs.getInt("stock")
        );
    }
}
