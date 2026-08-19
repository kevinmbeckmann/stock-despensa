package com.despensa.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Producto {
    private int id;
    private StringProperty nombre;
    private IntegerProperty cantidad;
    private ObjectProperty<LocalDate> fechaCaducidad;

    public Producto() {
        this.nombre = new SimpleStringProperty();
        this.cantidad = new SimpleIntegerProperty();
        this.fechaCaducidad = new SimpleObjectProperty<>();
    }

    public Producto(String nombre, int cantidad, LocalDate fechaCaducidad) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.fechaCaducidad = new SimpleObjectProperty<>(fechaCaducidad);
    }

    public Producto(int id, String nombre, int cantidad, LocalDate fechaCaducidad) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.fechaCaducidad = new SimpleObjectProperty<>(fechaCaducidad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad.get();
    }

    public ObjectProperty<LocalDate> fechaCaducidadProperty() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad.set(fechaCaducidad);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre.get() + '\'' +
                ", cantidad=" + cantidad.get() +
                ", fechaCaducidad=" + fechaCaducidad.get() +
                '}';
    }
}
