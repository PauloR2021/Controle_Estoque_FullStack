module com.pauloricardo.frontend_estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    // Abre o pacote de controllers para o FXMLLoader
    opens com.pauloricardo.frontend_estoque.Controller to javafx.fxml;

    // Exporta o pacote principal (MainApp)
    exports com.pauloricardo.frontend_estoque;
}