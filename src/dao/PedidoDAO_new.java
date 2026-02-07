package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.ConexionBD;
import model.Cliente;
import model.Pedido;

/**
 * DAO (Data Access Object) para la entidad Pedido
 * Maneja todas las operaciones de base de datos para pedidos
 */
public class PedidoDAO_new {

    /**
     * Inserta un nuevo pedido en la base de datos
     */
    public void crear(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, total, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, pedido.getCliente().getId());
            pstmt.setDouble(2, pedido.getTotal());
            pstmt.setString(3, pedido.getEstado().toString());
            pstmt.setString(4, pedido.getFechaPedido().toString());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int pedidoId = generatedKeys.getInt(1);
                        pedido.setId(pedidoId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear pedido: " + e.getMessage());
        }
    }

    /**
     * Obtiene un pedido por ID
     */
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAPedido(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedido: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Obtiene todos los pedidos
     */
    public List<Pedido> obtenerTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pedidos.add(mapearResultSetAPedido(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos: " + e.getMessage());
        }
        
        return pedidos;
    }

    /**
     * Actualiza un pedido existente
     */
    public void actualizar(Pedido pedido) {
        String sql = "UPDATE pedidos SET cliente_id = ?, total = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pedido.getCliente().getId());
            pstmt.setDouble(2, pedido.getTotal());
            pstmt.setString(3, pedido.getEstado().toString());
            pstmt.setInt(4, pedido.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    /**
     * Elimina un pedido de la base de datos
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
        }
    }

    /**
     * Mapea un ResultSet a un objeto Pedido
     */
    private Pedido mapearResultSetAPedido(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int clienteId = rs.getInt("cliente_id");
        String estado = rs.getString("estado");
        LocalDate fechaPedido = LocalDate.parse(rs.getString("fecha_pedido"));
        
        // Obtener el cliente desde ClienteDAO
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.obtenerPorId(clienteId);
        
        if (cliente == null) {
            System.err.println("Cliente no encontrado: " + clienteId);
            return null;
        }
        
        Pedido pedido = new Pedido(id, cliente, fechaPedido);
        pedido.setEstado(Pedido.EstadoPedido.valueOf(estado));
        
        return pedido;
    }
}
