module com.pauloricardo.frontend_estoque {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires java.net.http;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javafx.graphics;

    exports com.pauloricardo.frontend_estoque;

    // JavaFX
    opens com.pauloricardo.frontend_estoque.Controller to javafx.fxml;
    opens com.pauloricardo.frontend_estoque.Controller.Produto to javafx.fxml;

    opens com.pauloricardo.frontend_estoque.DTO.Produto
            to javafx.base, com.fasterxml.jackson.databind;

    opens com.pauloricardo.frontend_estoque.styles;
}
