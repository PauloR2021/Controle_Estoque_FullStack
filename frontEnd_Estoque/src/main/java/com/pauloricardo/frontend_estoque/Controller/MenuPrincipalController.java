package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Session.Session;
import com.pauloricardo.frontend_estoque.Util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    @FXML private Button btnProduto;
    @FXML private Button btnLogout;

    //Função para fazer Logout
    @FXML
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


    //Chamando a Tela de Produtos
    @FXML
    private void telaProduto() throws IOException{
        mudarTela("/com/pauloricardo/frontend_estoque/view/produto/produtos.fxml",
                btnProduto,
                "Sistema de Gestão de Estoque - Produto"
        );
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
        Scene scene = new Scene(root, 1200,600); //  tamanho da tela

        stage.setScene(scene);
        stage.setTitle("Logout - Sistema de Gestão de Estoque");
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();

    }

    //Função para Mudar de Tela
    public void mudarTela(String fxml, Button button, String title){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxml)
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setWidth(1200);
            stage.setHeight(600);
            stage.centerOnScreen();
            stage.show();
        }catch (Exception e){
            AlertUtil.erro("Erro: "+ e.getMessage());
            e.printStackTrace();
        }
    }

}
