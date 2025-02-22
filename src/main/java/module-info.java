module com.example.speedtypingtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.speedtypingtest to javafx.fxml;
    exports com.example.speedtypingtest;
}