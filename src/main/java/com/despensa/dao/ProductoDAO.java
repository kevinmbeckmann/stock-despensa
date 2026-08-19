package com.despensa.dao;

import com.despensa.model.Producto;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crearTabla() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "cantidad INTEGER NOT NULL, " +
                "fecha_caducidad DATE NOT NULL)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, cantidad, fecha_caducidad) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDate(3, Date.valueOf(producto.getFechaCaducidad()));
            pstmt.executeUpdate();
        }
    }

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
                productos.add(producto);
            }
        }
        
        return productos;
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, fecha_caducidad = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDate(3, Date.valueOf(producto.getFechaCaducidad()));
            pstmt.setInt(4, producto.getId());
            pstmt.executeUpdate();
        }
    }

    public void eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Producto> obtenerProductosPorCaducidad() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE fecha_caducidad <= date('now', '+7 days') ORDER BY fecha_caducidad";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
                productos.add(producto);
            }
        }
        
        return productos;
    }
}
