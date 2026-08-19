package com.despensa;

import com.despensa.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/despensa/view/Principal.fxml"));
            Scene scene = new Scene(root, 800, 600);
            
            // Cargar el archivo CSS para mejorar el diseño
            scene.getStylesheets().add(getClass().getResource("/com/despensa/view/styles.css").toExternalForm());
            
            primaryStage.setTitle("Gestión de Stock de Despensa");
            
            // Establecer ícono de la aplicación (si existe)
            try {
                primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/com/despensa/images/icon.png")));
            } catch (Exception e) {
                // Si no se encuentra el ícono, continuar sin él
            }
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
