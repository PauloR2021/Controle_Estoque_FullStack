package com.pauloricardo.frontend_estoque.Util;


import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertUtil {
    private static final String CSS_PATH =
            "/com/pauloricardo/frontend_estoque/styles/alert.css";

    // 🔹 Alerta de sucesso
    public static void sucesso(String mensagem) {
        criar(Alert.AlertType.INFORMATION, "Sucesso", mensagem);
    }

    // 🔹 Alerta de erro
    public static void erro(String mensagem) {
        criar(Alert.AlertType.ERROR, "Erro", mensagem);
    }

    // 🔹 Alerta de aviso
    public static void aviso(String mensagem) {
        criar(Alert.AlertType.WARNING, "Aviso", mensagem);
    }

    // 🔹 Alerta de confirmação
    public static boolean confirmacao(String mensagem) {
        Alert alert = criar(
                Alert.AlertType.CONFIRMATION,
                "Confirmação",
                mensagem
        );

        return alert.showAndWait()
                .filter(button -> button == javafx.scene.control.ButtonType.OK)
                .isPresent();
    }

    // 🔧 Método base reutilizável
    private static Alert criar(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(
                AlertUtil.class.getResource(CSS_PATH).toExternalForm()
        );

        dialogPane.getStyleClass().add("custom-alert");

        // Ícone (opcional)
        //Stage stage = (Stage) dialogPane.getScene().getWindow();
        //stage.getIcons().add(new Image("/icon.png"));

        alert.showAndWait();
        return alert;
    }
}
