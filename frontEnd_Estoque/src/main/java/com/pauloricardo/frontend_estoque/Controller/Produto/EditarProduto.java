package com.pauloricardo.frontend_estoque.Controller.Produto;

import com.pauloricardo.frontend_estoque.DTO.Produto.ProdutoResponseDTO;
import com.pauloricardo.frontend_estoque.Session.Session;
import com.pauloricardo.frontend_estoque.Util.AlertUtil;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EditarProduto {

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPreco;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtQuantidadeMinima;
    @FXML private CheckBox chkAtivo;
    @FXML private Button btnSalvar;
    private Boolean ativo;

    private ProdutoResponseDTO produto;

    public void setProduto(ProdutoResponseDTO produto){
        this.produto = produto;

        txtId.setText(produto.getId().toString());
        txtNome.setText(produto.getNome());
        txtCodigo.setText(produto.getCodigoBarras());
        txtPreco.setText(String.valueOf(produto.getPreco()));
        txtQuantidade.setText(produto.getQuantidade().toString());
        txtQuantidadeMinima.setText(produto.getQuantidadeMinima().toString());

    }

    @FXML
    private void salvar(){

        try{

            //Verificando se o Ativo está True ou False
            if (chkAtivo.isSelected()) {
                ativo = true;
            } else {
                ativo = false;
            }

            //Verificaindo os Campos
            if(txtNome.getText().isBlank() || txtCodigo.getText().isBlank()){
                AlertUtil.erro("Nome e Código de Barras são Obrigatórias");
                return;
            }

            // Monta JSON Manunalmente
            String json= """
                     {
                        "nome": "%s",
                        "codigoBarras":"%s",
                        "quantidade": %d,
                        "quantidadeMinima": %d,
                        "preco":%s,
                        "ativo":%b
                    } """.formatted(

                            txtNome.getText(),
                    txtCodigo.getText(),
                    Integer.parseInt(txtQuantidade.getText()),
                    Integer.parseInt(txtQuantidadeMinima.getText()),
                    txtPreco.getText().replace(",","."),
                    ativo
            );
            System.out.println("ID: " + txtId.getText());
            System.out.println("TOKEN FRONT: " + Session.getToken());
            System.out.println("JSON: " + json);

            //Chamando o HTTP para Enviar a API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/produto/"+txtId.getText()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Session.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){
                AlertUtil.aviso("Produto Atualizado com Sucesso !!");
            } else if (response.statusCode() == 201) {
                AlertUtil.aviso("Produto Atualizado com Sucesso !!");
                fecharJanela();
            } else if (response.statusCode() == 400) {
                AlertUtil.erro("Dados Inválidos");
            } else if (response.statusCode() == 401) {
                AlertUtil.erro("Sessão expirada. Faça Login Novamente");
                Session.clear();
            } else if (response.statusCode() == 403) {
                AlertUtil.erro("Você não tem Permissão para essa Ação");
            } else if (response.statusCode() == 409) {
                AlertUtil.erro("Erro de Validação");
            }else{
                AlertUtil.erro( "Erro inesperado (" + response.statusCode() + ")");
            }


        } catch (Exception e) {
            AlertUtil.erro("Falha ao Cadastrar produto: "+e.getMessage());
        }

    }


    @FXML
    private void cancelar(){
        fecharJanela();
    }

    public void fecharJanela(){
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }



}
