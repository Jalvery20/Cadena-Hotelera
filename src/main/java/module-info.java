module com.jalvery20.cadena_hotelera_visual {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.jalvery20.cadena_hotelera_visual to javafx.fxml;
    exports com.jalvery20.cadena_hotelera_visual;
}