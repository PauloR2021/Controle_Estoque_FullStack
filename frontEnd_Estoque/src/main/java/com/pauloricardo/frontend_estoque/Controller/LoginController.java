package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Service.AutenticacaoService;
import com.pauloricardo.frontend_estoque.Session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;

    private final AutenticacaoService autenticacaoService = new AutenticacaoService();

    @FXML
    private void onLogin() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.isBlank() || password.isBlank()){
            alert("Erro","Preencha todos os Campos");
            return;
        }

        boolean sucesso = autenticacaoService.login(username,password);

        if(sucesso) {
            alert("Sucesso", "Login realizado com Sucesso !");

           menuPrincipal("/com/pauloricardo/frontend_estoque/view/menu_principal.fxml");
        }else {
            alert("Erro","Usuário ou Senha Inválidos");
        }
    }

    private void alert(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait(); //.Bloqueia até clicar em OK
    }

    private void menuPrincipal(String caminho) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Scene scene = new Scene(root, 400,300); //  tamanho da tela

        stage.setScene(scene);
        stage.setTitle("Sistema de Gestão de Estoque");
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();

    }
}
