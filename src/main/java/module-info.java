module com.espoch.treeimplementation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.espoch.treeimplementation to javafx.fxml;

    exports com.espoch.treeimplementation.app;
    opens com.espoch.treeimplementation.app to javafx.fxml;
    exports com.espoch.treeimplementation.controller;
    opens com.espoch.treeimplementation.controller to javafx.fxml;
    exports com.espoch.treeimplementation.modelo.nodos;
    opens com.espoch.treeimplementation.modelo.nodos to javafx.fxml;
    exports com.espoch.treeimplementation.modelo.arbol;
    opens com.espoch.treeimplementation.modelo.arbol to javafx.fxml;
}