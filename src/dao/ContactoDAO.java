package com.sweetlab.dao;

import com.sweetlab.database.ConexionBD;
import com.sweetlab.model.Contacto;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {
    private ConexionBD conexion;

    public ContactoDAO() {
        this.conexion = new ConexionBD();
    }

    public void crear(Contacto contacto) throws SQLException {
        String sql = "INSERT INTO contactos (nombre, email, telefono, asunto, mensaje, fecha_contacto) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getEmail());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setString(4, contacto.getAsunto());
            pstmt.setString(5, contacto.getMensaje());
            pstmt.setTimestamp(6, Timestamp.valueOf(contacto.getFechaContacto()));
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    contacto.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Contacto> obtenerTodos() throws SQLException {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT * FROM contactos ORDER BY fecha_contacto DESC";
        
        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contacto contacto = mapResultSetToContacto(rs);
                contactos.add(contacto);
            }
        }
        return contactos;
    }

    public Contacto obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM contactos WHERE id = ?";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToContacto(rs);
                }
            }
        }
        return null;
    }

    public List<Contacto> obtenerPorAsunto(String asunto) throws SQLException {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT * FROM contactos WHERE asunto = ? ORDER BY fecha_contacto DESC";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, asunto);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Contacto contacto = mapResultSetToContacto(rs);
                    contactos.add(contacto);
                }
            }
        }
        return contactos;
    }

    public void actualizar(Contacto contacto) throws SQLException {
        String sql = "UPDATE contactos SET nombre = ?, email = ?, telefono = ?, asunto = ?, mensaje = ? WHERE id = ?";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getEmail());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setString(4, contacto.getAsunto());
            pstmt.setString(5, contacto.getMensaje());
            pstmt.setInt(6, contacto.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM contactos WHERE id = ?";
        
        try (Connection conn = conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Contacto mapResultSetToContacto(ResultSet rs) throws SQLException {
        Contacto contacto = new Contacto();
        contacto.setId(rs.getInt("id"));
        contacto.setNombre(rs.getString("nombre"));
        contacto.setEmail(rs.getString("email"));
        contacto.setTelefono(rs.getString("telefono"));
        contacto.setAsunto(rs.getString("asunto"));
        contacto.setMensaje(rs.getString("mensaje"));
        
        Timestamp timestamp = rs.getTimestamp("fecha_contacto");
        if (timestamp != null) {
            contacto.setFechaContacto(timestamp.toLocalDateTime());
        }
        
        return contacto;
    }
}
