package com.pauloricardo.frontend_estoque.Controller;


import com.pauloricardo.frontend_estoque.Util.AlertUtil;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class CriarUsuario {

    @FXML private TextField txtNome;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private TextField textFieldSenha;
    @FXML private TextField txtEmail;
    @FXML private Button btnCriar;


    @FXML
    private boolean senhaVisivel = false;

    @FXML
    public void cadastrar(){
        try{

            if (txtNome.getText().isBlank() || txtUsuario.getText().isBlank() || txtEmail.getText().isBlank()){
                AlertUtil.aviso("Nome, Usuário e Email não podem ser Vazios!");
                return;
            }

            String senha = getSenhaDigitada();

            if (senha == null || senha.isBlank() || senha.length() < 8) {
                AlertUtil.aviso( "A senha deve ter no mínimo 8 caracteres");
                return;
            }

            if (!senha.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
                AlertUtil.aviso("A senha deve conter letras e números");
                return;
            }

            //Montando o Json manualmente
            String json = """
                         {
                         	"nome":"%s",
                        	"username":"%s",
                        	"password":"%s",
                        	"role":"USER",
                        	"ativo":false,
                        	"email":"%s",
                        	"codigoVerificacao":""
                        }
                    """.formatted(

                    txtNome.getText(),
                    txtUsuario.getText(),
                    senha,
                    txtEmail.getText());


            //Chamando a API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpClient client =HttpClient.newHttpClient();

            System.out.println(request);
            System.out.println(client);


            HttpResponse<String> response=
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            //Retornos da API  Validando os o Cadastros e Erros

            if(response.statusCode() == 200){
                AlertUtil.sucesso("Usuário Cadastrado com Sucesso! Realize a Validação do Código de Confirmação");
                limparCampos();
                abrirValidacaoCodigo("/com/pauloricardo/frontend_estoque/view/validando-codigo.fxml");
            } else if (response.statusCode() == 400) {
                AlertUtil.erro("Usuário Já existe Cadastrado");
            } else if (response.statusCode() == 500) {
                AlertUtil.erro("Sem Retorno do Servidor como o APP");
            }else{
                AlertUtil.erro("Erro no Cadastro");
                System.out.println(response.statusCode());
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void mostrarSenha(){
        if (senhaVisivel) {
            ocultarSenha();
            return;
        }

        // Mostrar senha
        textFieldSenha.setText(txtSenha.getText());

        txtSenha.setVisible(false);
        txtSenha.setManaged(false);

        textFieldSenha.setVisible(true);
        textFieldSenha.setManaged(true);

        senhaVisivel = true;

        // ⏱ Tempo visível (3 segundos)
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> ocultarSenha());
        pause.play();
        
    }

    @FXML
    private void login(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pauloricardo/frontend_estoque/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCriar.getScene().getWindow();
            Scene scene = new Scene(root,1200,600); //Tamanho da Tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Login");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            AlertUtil.erro(e.getMessage());
            throw new RuntimeException(e);

        }

    }
    private void ocultarSenha() {
        txtSenha.setText(textFieldSenha.getText());

        txtSenha.setVisible(true);
        txtSenha.setManaged(true);

        textFieldSenha.setVisible(false);
        textFieldSenha.setManaged(false);

        senhaVisivel = false;
    }
    

    private void limparCampos(){
        txtNome.clear();
        txtEmail.clear();
        txtSenha.clear();
        txtSenha.clear();
    }

    private void abrirValidacaoCodigo(String caminho) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent root = loader.load();
            Stage stage = (Stage) btnCriar.getScene().getWindow();
            Scene scene = new Scene(root,1200,600); //Tamanho da Tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Validação do Usuário");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            AlertUtil.erro(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private String getSenhaDigitada() {
        return txtSenha.isVisible()
                ? txtSenha.getText()
                : textFieldSenha.getText();
    }


}
