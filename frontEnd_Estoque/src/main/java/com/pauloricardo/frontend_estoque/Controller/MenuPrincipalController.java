package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    @FXML private Button btnLogout;

    @FXML //Função para fazer Logout
    private void logout() throws IOException {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Deseja Realmente Sair");

        alert.showAndWait().ifPresent(button -> {
            if(button == ButtonType.OK){
                try {
                    Session.clear(); //Remove Token
                    voltarLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @FXML //Função para Fechar o Sistema
    private void sair(){
        System.exit(0);
    }

    //Função para Voltar a tela de Login
    private void voltarLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        Scene scene = new Scene(root, 400,300); //  tamanho da tela

        stage.setScene(scene);
        stage.setTitle("Logout - Sistema de Gestão de Estoque");
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();

    }


}
