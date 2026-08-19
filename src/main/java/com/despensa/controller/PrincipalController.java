package com.despensa.controller;

import com.despensa.dao.ProductoDAO;
import com.despensa.database.DatabaseConnection;
import com.despensa.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrincipalController {

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> columnaId;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, Integer> columnaCantidad;
    @FXML
    private TableColumn<Producto, LocalDate> columnaCaducidad;

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoCantidad;
    @FXML
    private DatePicker campoFechaCaducidad;

    @FXML
    private Button botonAgregar;
    @FXML
    private Button botonActualizar;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonLimpiar;

    private ObservableList<Producto> listaProductos;
    private ProductoDAO productoDAO;
    private Producto productoSeleccionado;

    public void initialize() {
        try {
            // Inicializar la conexión a la base de datos
            productoDAO = new ProductoDAO(DatabaseConnection.getConnection());
            productoDAO.crearTabla();
            
            // Configurar la tabla
            columnaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
            columnaCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
            columnaCaducidad.setCellValueFactory(cellData -> cellData.getValue().fechaCaducidadProperty());
            
            // Cargar los productos
            cargarProductos();
            
            // Configurar la selección en la tabla
            tablaProductos.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        productoSeleccionado = newValue;
                        if (productoSeleccionado != null) {
                            campoNombre.setText(productoSeleccionado.getNombre());
                            campoCantidad.setText(String.valueOf(productoSeleccionado.getCantidad()));
                            campoFechaCaducidad.setValue(productoSeleccionado.getFechaCaducidad());
                        }
                    });
            
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo inicializar la aplicación");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = productoDAO.obtenerTodosLosProductos();
            listaProductos = FXCollections.observableArrayList(productos);
            tablaProductos.setItems(listaProductos);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron cargar los productos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAgregar() {
        try {
            String nombre = campoNombre.getText().trim();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            LocalDate fechaCaducidad = campoFechaCaducidad.getValue();

            if (nombre.isEmpty() || cantidad <= 0 || fechaCaducidad == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Datos incompletos");
                alert.setContentText("Por favor, complete todos los campos correctamente.");
                alert.showAndWait();
                return;
            }

            Producto producto = new Producto(nombre, cantidad, fechaCaducidad);
            productoDAO.insertarProducto(producto);

            // Limpiar campos
            campoNombre.clear();
            campoCantidad.clear();
            campoFechaCaducidad.setValue(null);

            // Recargar la tabla
            cargarProductos();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Formato incorrecto");
            alert.setContentText("La cantidad debe ser un número válido.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo agregar el producto");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleActualizar() {
        if (productoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Ningún producto seleccionado");
            alert.setContentText("Por favor, seleccione un producto para actualizar.");
            alert.showAndWait();
            return;
        }

        try {
            String nombre = campoNombre.getText().trim();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            LocalDate fechaCaducidad = campoFechaCaducidad.getValue();

            if (nombre.isEmpty() || cantidad <= 0 || fechaCaducidad == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Datos incompletos");
                alert.setContentText("Por favor, complete todos los campos correctamente.");
                alert.showAndWait();
                return;
            }

            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setCantidad(cantidad);
            productoSeleccionado.setFechaCaducidad(fechaCaducidad);

            productoDAO.actualizarProducto(productoSeleccionado);

            // Limpiar campos
            campoNombre.clear();
            campoCantidad.clear();
            campoFechaCaducidad.setValue(null);
            productoSeleccionado = null;

            // Recargar la tabla
            cargarProductos();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Formato incorrecto");
            alert.setContentText("La cantidad debe ser un número válido.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo actualizar el producto");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEliminar() {
        if (productoSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Ningún producto seleccionado");
            alert.setContentText("Por favor, seleccione un producto para eliminar.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar producto");
        alert.setContentText("¿Está seguro de que desea eliminar el producto \"" + 
                            productoSeleccionado.getNombre() + "\"?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                productoDAO.eliminarProducto(productoSeleccionado.getId());

                // Limpiar campos
                campoNombre.clear();
                campoCantidad.clear();
                campoFechaCaducidad.setValue(null);
                productoSeleccionado = null;

                // Recargar la tabla
                cargarProductos();

            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("No se pudo eliminar el producto");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        campoNombre.clear();
        campoCantidad.clear();
        campoFechaCaducidad.setValue(null);
        productoSeleccionado = null;
        tablaProductos.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleMostrarCaducados() {
        try {
            List<Producto> productosCaducados = productoDAO.obtenerProductosPorCaducidad();
            
            if (productosCaducados.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Productos por caducar");
                alert.setHeaderText("No hay productos próximos a caducar");
                alert.setContentText("No hay productos que estén próximos a su fecha de vencimiento.");
                alert.showAndWait();
            } else {
                StringBuilder mensaje = new StringBuilder("Productos próximos a caducar:\n\n");
                for (Producto p : productosCaducados) {
                    mensaje.append("- ").append(p.getNombre())
                           .append(" (").append(p.getCantidad()).append(" unidades)")
                           .append(" - Vence: ").append(p.getFechaCaducidad()).append("\n");
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Productos por caducar");
                alert.setHeaderText("Productos próximos a caducar");
                alert.setContentText(mensaje.toString());
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron obtener los productos por caducar");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
