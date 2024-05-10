module com.example.hhd {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.hhd to javafx.fxml;
    exports com.example.hhd;
    requires javafx.web;
    requires jlayer;
}