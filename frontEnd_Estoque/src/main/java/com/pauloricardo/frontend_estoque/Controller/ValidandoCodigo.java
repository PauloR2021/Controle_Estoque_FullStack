package com.pauloricardo.frontend_estoque.Controller;

import com.pauloricardo.frontend_estoque.Session.Session;
import com.pauloricardo.frontend_estoque.Util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ValidandoCodigo {

    @FXML private TextField txtEmail;
    @FXML private TextField txtCodigo;
    @FXML private Button btnValidar;


    @FXML
    private void telalogin(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnValidar.getScene().getWindow();
            Scene scene = new Scene(root,1200,600); //Tamanho da Tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Validação do Usuário");
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();
        } catch (Exception e) {
            AlertUtil.erro("Erro: "+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void validando() {

        try{

            //Verificando os Campos

            if(txtEmail.getText().isBlank() || txtCodigo.getText().isBlank() ){
                AlertUtil.aviso("Preencha o Email e o Código para Válidar");
                return;
            }

            //Criando o Json

            String json =
                    """
                        {
                            "email":"%s",
                            "codigo": "%s"
                        }
                    """.formatted(
                            txtEmail.getText(),
                            txtCodigo.getText()
                    );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/auth/validar-codigo"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response=
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            //Retornos da API  Validando os o Cadastros e Erros

            if(response.statusCode() == 200){
                AlertUtil.sucesso("Usuário Validado com Sucesso!");
                limparCampos();
                abrirLogin("/com/pauloricardo/frontend_estoque/view/login.fxml");
            } else if (response.statusCode() == 400) {
                AlertUtil.erro("Usuário não Validado");
            } else if (response.statusCode() == 500) {
                AlertUtil.erro("Sem Retorno do Servidor como o APP");
            }else{
                AlertUtil.erro("Erro no Cadastro");
                System.out.println(response.statusCode());
            }



        } catch (Exception e) {
            AlertUtil.erro("Error: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtEmail.clear();
    }

    private void abrirLogin(String caminho) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();
        Stage stage = (Stage) btnValidar.getScene().getWindow();
        Scene scene = new Scene(root,1200,600); //Tamanho da Tela

        stage.setScene(scene);
        stage.setTitle("Sistema de Gestão de Estoque - Validação do Usuário");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

}
