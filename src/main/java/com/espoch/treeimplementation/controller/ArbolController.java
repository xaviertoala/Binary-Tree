package com.espoch.treeimplementation.controller;

import com.espoch.treeimplementation.modelo.arbol.ArbolGeneral;
import com.espoch.treeimplementation.modelo.nodos.Nodo;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ArbolController {

    @FXML
    private TextField txtValor;

    @FXML
    private Canvas canvas;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lblMensaje;

    private ArbolGeneral<Integer> arbol;
    private static final double NODE_RADIUS = 20;
    private static final double VERTICAL_GAP = 60;

    // Zoom y Pan
    private double zoomFactor = 1.0;
    private static final double ZOOM_SPEED = 0.1;
    private static final double MIN_ZOOM = 0.2;
    private static final double MAX_ZOOM = 5.0;

    public void initialize() {
        arbol = new ArbolGeneral<>();

        // Listener para redimensionar el canvas cuando cambia el tamaño de la ventana
        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
            dibujarArbol();
        });

        // Agregar evento de zoom con el scroll del mouse
        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.isControlDown()) {
                double delta = event.getDeltaY();
                if (delta > 0) {
                    zoomFactor = Math.min(MAX_ZOOM, zoomFactor + ZOOM_SPEED);
                } else {
                    zoomFactor = Math.max(MIN_ZOOM, zoomFactor - ZOOM_SPEED);
                }
                dibujarArbol();
                event.consume();
            }
        });

        limpiarCanvas();
    }

    @FXML
    void onAgregar() {
        try {
            int valor = Integer.parseInt(txtValor.getText());
            if (arbol.insertar(valor)) {
                lblMensaje.setText("Valor " + valor + " agregado correctamente.");
                lblMensaje.setTextFill(Color.web("#27ae60"));
            } else {
                lblMensaje.setText("El valor " + valor + " ya existe en el árbol.");
                lblMensaje.setTextFill(Color.web("#f1c40f"));
            }
            txtValor.clear();
            dibujarArbol();
        } catch (NumberFormatException e) {
            lblMensaje.setText("Por favor, ingrese un número entero válido.");
            lblMensaje.setTextFill(Color.web("#e74c3c"));
        }
    }

    @FXML
    void onEliminar() {
        try {
            int valor = Integer.parseInt(txtValor.getText());
            if (arbol.eliminar(valor)) {
                lblMensaje.setText("Valor " + valor + " eliminado correctamente.");
                lblMensaje.setTextFill(Color.web("#27ae60"));
            } else {
                lblMensaje.setText("El valor " + valor + " no se encuentra en el árbol.");
                lblMensaje.setTextFill(Color.web("#e74c3c"));
            }
            txtValor.clear();
            dibujarArbol();
        } catch (NumberFormatException e) {
            lblMensaje.setText("Por favor, ingrese un número entero válido.");
            lblMensaje.setTextFill(Color.web("#e74c3c"));
        }
    }

    @FXML
    void onLimpiar() {
        arbol = new ArbolGeneral<>();
        zoomFactor = 1.0;
        lblMensaje.setText("Árbol limpiado.");
        lblMensaje.setTextFill(Color.WHITE);
        dibujarArbol();
    }

    private void limpiarCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void dibujarArbol() {
        if (arbol.getRaiz() == null) {
            // Ajustar canvas al tamaño del viewport si está vacío
            canvas.setWidth(scrollPane.getViewportBounds().getWidth());
            canvas.setHeight(scrollPane.getViewportBounds().getHeight());
            limpiarCanvas();
            return;
        }

        int altura = calcularAltura(arbol.getRaiz());
        double hGapInicial = Math.pow(2, altura - 1) * NODE_RADIUS * 1.5;

        // Calcular dimensiones del árbol con zoom
        double treeWidthNeeded = (hGapInicial * 2 + NODE_RADIUS * 6) * zoomFactor;
        double treeHeightNeeded = (altura * VERTICAL_GAP + NODE_RADIUS * 6) * zoomFactor;

        // El canvas debe ser al menos el tamaño del viewport para centrado, o mayor
        // para scroll
        double viewWidth = scrollPane.getViewportBounds().getWidth();
        double viewHeight = scrollPane.getViewportBounds().getHeight();

        canvas.setWidth(Math.max(treeWidthNeeded, viewWidth));
        canvas.setHeight(Math.max(treeHeightNeeded, viewHeight));

        limpiarCanvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();

        // Punto de inicio (centrado horizontalmente en el canvas)
        double centerX = canvas.getWidth() / 2;
        double centerY = 50 * zoomFactor;

        // Aplicar zoom
        gc.translate(centerX, centerY);
        gc.scale(zoomFactor, zoomFactor);
        gc.translate(-centerX, -centerY);

        dibujarNodo(gc, arbol.getRaiz(), centerX, centerY, hGapInicial);

        gc.restore();
    }

    private void dibujarNodo(GraphicsContext gc, Nodo<Integer> nodo, double x, double y, double hGap) {
        // Dibujar conexiones
        if (nodo.izquierdo != null) {
            gc.setStroke(Color.web("#34495e"));
            gc.setLineWidth(2 / zoomFactor);
            gc.strokeLine(x, y, x - hGap, y + VERTICAL_GAP);
            dibujarNodo(gc, nodo.izquierdo, x - hGap, y + VERTICAL_GAP, hGap / 2);
        }

        if (nodo.derecho != null) {
            gc.setStroke(Color.web("#34495e"));
            gc.setLineWidth(2 / zoomFactor);
            gc.strokeLine(x, y, x + hGap, y + VERTICAL_GAP);
            dibujarNodo(gc, nodo.derecho, x + hGap, y + VERTICAL_GAP, hGap / 2);
        }

        // Dibujar el nodo
        gc.setFill(Color.web("#3498db"));
        gc.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setStroke(Color.web("#2980b9"));
        gc.setLineWidth(2 / zoomFactor);
        gc.strokeOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Dibujar el valor
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("System Bold", 14));
        String texto = String.valueOf(nodo.valor);

        double textWidth = gc.getFont().getSize() * 0.6 * texto.length();
        gc.fillText(texto, x - (textWidth / 2) + 2, y + 5);
    }

    private int calcularAltura(Nodo<Integer> nodo) {
        if (nodo == null)
            return 0;
        return 1 + Math.max(calcularAltura(nodo.izquierdo), calcularAltura(nodo.derecho));
    }
}
