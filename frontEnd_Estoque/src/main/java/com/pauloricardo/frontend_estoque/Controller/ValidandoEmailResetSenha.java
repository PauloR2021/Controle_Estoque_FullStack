package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ValidandoEmailResetSenha {


    @FXML private TextField txtEmail;
    @FXML private Button btnValidacao;


    @FXML
    private void telaResetSenha(){

        try{
            //Validando dados

            if(txtEmail.getText().isBlank()){
                AlertUtil.erro("Preencha o Campo de Email");
                return;
            }

            String json =
                    """
                    {"email":"%s"}
                    """.formatted(txtEmail.getText());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/auth/reset-codigo"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String > response =
                    client.send(request,HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                AlertUtil.sucesso( "Código Enviado com Sucesso para Validação!");
                txtEmail.clear();
                abrirResetSenha("/com/pauloricardo/frontend_estoque/view/reset-senha.fxml");
            } else if (response.statusCode() == 400) {
                AlertUtil.erro("Não Foi Possível enviar Código de Validação");
            } else if (response.statusCode() == 500) {
                AlertUtil.erro("Sem Retorno do Servidor como o APP");
            }else{
                AlertUtil.erro("Erro no Cadastro");
                System.out.println(response.statusCode());
            }
        } catch (Exception e) {
            AlertUtil.erro("Erro"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private void abrirResetSenha(String caminho) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();
        Stage stage = (Stage) btnValidacao.getScene().getWindow();
        Scene scene = new Scene(root,1200,600); //Tamanho da Tela

        stage.setScene(scene);
        stage.setTitle("Sistema de Gestão de Estoque - Reset Senha");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

}
