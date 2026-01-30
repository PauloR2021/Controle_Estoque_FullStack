package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Service.AutenticacaoService;
import com.pauloricardo.frontend_estoque.Util.AlertUtil;
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
    @FXML private Button btnCadastro;
    @FXML private Button btnReset;

    private final AutenticacaoService autenticacaoService = new AutenticacaoService();

    //Função para Validar o Login do Usuário
    @FXML
    private void onLogin() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.isBlank() || password.isBlank()){
            AlertUtil.erro("Preencha todos os Campos");
            return;
        }

        boolean sucesso = autenticacaoService.login(username,password);

        if(sucesso) {
            AlertUtil.sucesso( "Login realizado com Sucesso !");

           menuPrincipal("/com/pauloricardo/frontend_estoque/view/menu_principal.fxml");
        }else {
            AlertUtil.erro("Usuário ou Senha Inválidos");
        }
    }

    @FXML
    private void cadastrar(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/criar-usuario.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root, 1200,600); //  tamanho da tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Criar Usuário");
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();

        }catch (Exception e){
            AlertUtil.erro("Erro: "+e.getMessage());
        }


    }


    @FXML
    private void resetSenha(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/validando-email-reset-senha.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnReset.getScene().getWindow();
            Scene scene = new Scene(root, 1200,600); //  tamanho da tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Validando Reset Senha");
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();

        }catch (Exception e){
            AlertUtil.erro("Erro: "+ e.getMessage());
        }

    }

    //Função para Chamar o Menu Principal do APP
    private void menuPrincipal(String caminho) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Scene scene = new Scene(root, 1200,600); //  tamanho da tela

        stage.setScene(scene);
        stage.setTitle("Sistema de Gestão de Estoque");
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();

    }
}
