package com.pauloricardo.frontend_estoque.Controller.Produto;

import com.pauloricardo.frontend_estoque.Session.Session;
import com.pauloricardo.frontend_estoque.Util.AlertUtil;
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
                AlertUtil.erro("Nome e Código de Barras são Obrigatórias");
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
            
            
            if(response.statusCode() == 200){
                AlertUtil.sucesso("Produto Cadastrado com Sucesso !!");
                limparCampos();
            } else if (response.statusCode() == 2001) {
                AlertUtil.sucesso("Produto Cadastrado com Sucesso !!");
                limparCampos();
            } else if (response.statusCode() == 400) {
                AlertUtil.aviso( "Dados inválidos");
            } else if (response.statusCode()== 401) {
                AlertUtil.aviso("Dados inválidos");
                Session.clear();
            } else if (response.statusCode() == 403) {
                AlertUtil.erro("Você não tem permissão para essa ação");
            } else if (response.statusCode() == 409) {
                AlertUtil.erro("Produto já cadastrado");
            }else{
                AlertUtil.erro( "Erro inesperado (" + response.statusCode() + ")");
            }

        }catch (Exception e){
            AlertUtil.erro("Falha ao Cadastrar produto: "+e.getMessage());
        }

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
