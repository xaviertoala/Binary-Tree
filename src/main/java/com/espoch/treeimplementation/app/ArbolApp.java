package com.espoch.treeimplementation.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ArbolApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // El FXML está en com/espoch/treeimplementation/arbol-view.fxml relativo a
        // resources
        // Pero como ArbolApp está en com.espoch.treeimplementation.app, subimos un
        // nivel
        FXMLLoader fxmlLoader = new FXMLLoader(
                ArbolApp.class.getResource("/com/espoch/treeimplementation/arbol-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Visualizador de Árbol Binario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
    }
}
