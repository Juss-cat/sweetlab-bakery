package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.ConexionBD;
import model.Cliente;

/**
 * DAO (Data Access Object) para la entidad Cliente
 * Maneja todas las operaciones de base de datos para clientes
 */
public class ClienteDAO {

    /**
     * Inserta un nuevo cliente en la base de datos
     */
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getDireccion());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene un cliente por su ID
     */
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Obtiene todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(mapearResultSetACliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }

    /**
     * Actualiza un cliente existente
     */
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setInt(5, cliente.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina un cliente de la base de datos
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Cliente
     */
    private Cliente mapearResultSetACliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                rs.getString("ciudad") != null ? rs.getString("ciudad") : "",
                rs.getString("codigo_postal") != null ? rs.getString("codigo_postal") : ""
        );
    }
}
