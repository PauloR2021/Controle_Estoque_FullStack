package com.pauloricardo.frontend_estoque.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ResetSenha {

    @FXML private TextField txtEmail;
    @FXML private TextField txtCode;
    @FXML private TextField txtSenha;
    @FXML private TextField textFieldSenha;

    @FXML private Button btnLogin;
    @FXML private Button btnMostrarSenha;
    @FXML private Button btnCriar;

    @FXML
    private boolean senhaVisivel = false;

    @FXML
    public void cadastrarNovaSenha(){
        try{

            if (txtEmail.getText().isBlank() || txtCode.getText().isBlank()){
                mensagem("Erro nos Dados", "Email e Email não podem ser Vazios!");
                return;
            }

            String senha = getSenhaDigitada();

            if (senha == null || senha.isBlank() || senha.length() < 8) {
                mensagem("Erro na Senha", "A senha deve ter no mínimo 8 caracteres");
                return;
            }

            if (!senha.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
                mensagem("Erro na Senha",
                        "A senha deve conter letras e números");
                return;
            }

            //Montando o Json manualmente
            String json = """
                         {
                         	"email":"%s",
                            "code":"%s",
                            "novaSenha":"%s"
                        }
                    """.formatted(

                    txtEmail.getText(),
                    txtCode.getText(),
                    senha);

            //Chamando a API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/auth/reset-password"))
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
                mensagem("Senha Alterada", "Senha Alterada com Sucesso!");
                txtCode.clear();
                txtEmail.clear();
                abrirLogin("/com/pauloricardo/frontend_estoque/view/login.fxml");
            } else if (response.statusCode() == 400) {
                mensagem("Erro","Não Possível Alterar a Senha ");
            } else if (response.statusCode() == 500) {
                mensagem("Erro","Sem Retorno do Servidor como o APP");
            }else{
                mensagem("Erro","Erro no Cadastro");
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
            mensagem("Error",e.getMessage());
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

    private void abrirLogin(String caminho) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent root = loader.load();
            Stage stage = (Stage) btnCriar.getScene().getWindow();
            Scene scene = new Scene(root,1200,600); //Tamanho da Tela

            stage.setScene(scene);
            stage.setTitle("Sistema de Gestão de Estoque - Login");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            mensagem("Error",e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private String getSenhaDigitada() {
        return txtSenha.isVisible()
                ? txtSenha.getText()
                : textFieldSenha.getText();
    }

    private void mensagem(String titulo, String msg){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
