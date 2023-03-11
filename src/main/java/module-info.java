module com.example.haromszogek {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.haromszogek to javafx.fxml;
    exports com.example.haromszogek;
}