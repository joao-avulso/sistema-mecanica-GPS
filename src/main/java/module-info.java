module br.com.gps.mecanica {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.gps.mecanica to javafx.fxml;
    exports br.com.gps.mecanica;
}
