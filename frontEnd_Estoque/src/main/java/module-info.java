module com.pauloricardo.frontend_estoque {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pauloricardo.frontend_estoque to javafx.fxml;
    exports com.pauloricardo.frontend_estoque;
}