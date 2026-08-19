package com.despensa.model;

import java.time.LocalDate;

public class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private LocalDate fechaCaducidad;

    public Producto() {}

    public Producto(String nombre, int cantidad, LocalDate fechaCaducidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Producto(int id, String nombre, int cantidad, LocalDate fechaCaducidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechaCaducidad=" + fechaCaducidad +
                '}';
    }
}
