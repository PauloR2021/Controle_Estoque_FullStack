package com.pauloricardo.frontend_estoque.Controller.Produto;

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

public class ProdutoController {

    @FXML private StackPane contentPane;
    @FXML private Button btnCadastrar;
    @FXML private Button btnListar;
    @FXML private Button btnLogout;

    //Função do Botão de Cadastro
    @FXML
    private void abriCadastro(){
        setConteudo("produto-cadastrar.fxml");
    }

    //Funcção do Botão de Abrir Lista
    @FXML
    private void abriLista(){
        setConteudo("listar-produto.fxml");
    }


    //Função do Botão de Logout
    @FXML void logout() throws IOException{
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

    //Função para Setar as Telas de Cadastro e Listagem de Produto
    public void setConteudo(String fxml){
        try{
            contentPane.getChildren().clear();
            contentPane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/com/pauloricardo/frontend_estoque/view/produto/" +fxml))
            );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Função para Voltar a tela de Login
    private void voltarLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        Scene scene = new Scene(root, 300,400); //  tamanho da tela

        stage.setScene(scene);
        stage.setTitle("Logout - Sistema de Gestão de Estoque");
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.show();

    }


}
