module org.example.powtorzenie_do_drugiego_kolokwium {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.powtorzenie_do_drugiego_kolokwium to javafx.fxml;
    exports org.example.powtorzenie_do_drugiego_kolokwium;
}