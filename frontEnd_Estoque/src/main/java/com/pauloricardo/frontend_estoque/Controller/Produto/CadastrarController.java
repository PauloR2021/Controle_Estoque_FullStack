package com.pauloricardo.frontend_estoque.Controller.Produto;

import com.pauloricardo.frontend_estoque.Session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class CadastrarController {

    @FXML private TextField textNome;
    @FXML private TextField textCodigo;
    @FXML private TextField textQuantidade;
    @FXML private TextField txtMinimo;
    @FXML private TextField txtPreco;
    @FXML private Button btnCadastrar;


    @FXML
    public void cadastrar(){
        try{
            //Verificando os Campos
            if(textNome.getText().isBlank() || textCodigo.getText().isBlank()){
                mostrarAlerta("Erro","Nome e Código de Barras são Obrigatórias");
                return;
            }

            // 🔹 Monta JSON manualmente
            String json = """
                {
                  "nome": "%s",
                  "codigoBarras": "%s",
                  "quantidade": %d,
                  "quantidadeMinima": %d,
                  "preco": %s,
                  "ativo": true
                }
                """.formatted(
                    textNome.getText(),
                    textCodigo.getText(),
                    Integer.parseInt(textQuantidade.getText()),
                    Integer.parseInt(txtMinimo.getText()),
                    txtPreco.getText().replace(",", ".")
            );
            System.out.println("TOKEN FRONT: " + Session.getToken());


            //Chamando o HTTP para enviar na API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/produto"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Session.getToken()) //Colocando a Autenticação do Usuário
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            //Retornos da API  Validando os o Cadastros e Erros
            switch (response.statusCode()) {
                case 200 -> mostrarAlerta("Cadastrado","Produto Cadastrado com Sucesso !!");
                case 201 -> mostrarAlerta("Cadastrado", "Produto Cadastrado com Sucesso !!");
                case 400 -> mostrarAlerta("Erro", "Dados inválidos");
                case 401 -> {
                    mostrarAlerta("Erro", "Sessão expirada. Faça login novamente.");
                    Session.clear();
                }
                case 403 -> mostrarAlerta("Erro", "Você não tem permissão para essa ação");
                case 409 -> mostrarAlerta("Erro", "Produto já cadastrado");
                default -> mostrarAlerta("Erro", "Erro inesperado (" + response.statusCode() + ")");
            }


        }catch (Exception e){
            mostrarAlerta("Erro","Falha ao Cadastrar produto: "+e.getMessage());
        }

    }

    //Metodo para Mostar Mensagens do Retorno da API
    private void mostrarAlerta(String titulo, String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    //Limpar Campos
    private void limparCampos() {
        textNome.clear();
        textCodigo.clear();
        textQuantidade.clear();
        txtMinimo.clear();
        txtPreco.clear();
    }




}
