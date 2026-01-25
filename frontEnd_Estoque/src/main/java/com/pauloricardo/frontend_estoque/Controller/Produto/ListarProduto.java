package com.pauloricardo.frontend_estoque.Controller.Produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pauloricardo.frontend_estoque.DTO.Produto.ProdutoResponseDTO;
import com.pauloricardo.frontend_estoque.Session.Session;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.SeekableByteChannel;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ListarProduto {

    @FXML
    private TableView<ProdutoResponseDTO> tabelaProduto;

    @FXML
    private TableColumn<ProdutoResponseDTO, Integer> colId;

    @FXML
    private TableColumn<ProdutoResponseDTO, String> colNome;

    @FXML
    private TableColumn<ProdutoResponseDTO, String> colCodigo;

    @FXML
    private TableColumn<ProdutoResponseDTO, BigDecimal> colPreco;

    @FXML
    private TableColumn<ProdutoResponseDTO, Integer> colQuantidade;

    @FXML
    private TableColumn<ProdutoResponseDTO, Integer> colQuantidadeMinina;

    @FXML
    private TableColumn<ProdutoResponseDTO, Boolean> colAtivo;

    @FXML
    private TableColumn<ProdutoResponseDTO, LocalDateTime> colData;

    @FXML
    private TableColumn<ProdutoResponseDTO, Void> colAcoes;

    private final String TOKEN = Session.getToken();

    private ObservableList<ProdutoResponseDTO> listaProdutos;


    @FXML
    public void initialize() {

        System.out.println("Controller carregado ✔");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setStyle("-fx-alignment: CENTER;");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setStyle("-fx-alignment: CENTER;");

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colCodigo.setStyle("-fx-alignment: CENTER;");

        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPreco.setStyle("-fx-alignment: CENTER;");

        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colQuantidade.setStyle("-fx-alignment: CENTER;");

        colQuantidadeMinina.setCellValueFactory(new PropertyValueFactory<>("quantidadeMinima"));
        colQuantidadeMinina.setStyle("-fx-alignment: CENTER;");

        colAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
        colAtivo.setStyle("-fx-alignment: CENTER;");

        colData.setCellValueFactory(new PropertyValueFactory<>("criacao"));
        colData.setStyle("-fx-alignment: CENTER;");

        configuraColunaAcoes(); //Cria Botões de Editar e Excluir

        carregarProdutos();
    }


    private void carregarProdutos(){

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/produto"))
                    .header("Authorization", "Bearer " + TOKEN)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<ProdutoResponseDTO> produtos = Arrays.asList(
                        mapper.readValue(response.body(), ProdutoResponseDTO[].class)
                );

                tabelaProduto.setItems(FXCollections.observableArrayList(produtos));

            } else if (response.statusCode() == 401) {
                alert("Sessão expirada. Faça login novamente.");
            } else if (response.statusCode() == 403) {
                alert("Você não tem permissão para acessar.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            alert("Erro ao carregar produtos");
        }

    }

    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    //Cria os Botões
    private void configuraColunaAcoes(){
        colAcoes.setCellFactory(col -> new TableCell<>(){
            private  final Button btnEditar = new Button("Editar" );
            private  final Button btnExcluir = new Button("Excluir");
            private  final HBox hbox = new HBox(5,btnEditar,btnExcluir);
            {
                btnEditar.setOnAction(event -> {
                    ProdutoResponseDTO produto =
                            getTableView().getItems().get(getIndex());
                    editarProduto(produto);
                });

                btnExcluir.setOnAction(event -> {
                    ProdutoResponseDTO produto =
                            getTableView().getItems().get(getIndex());
                    excluirProduto(produto);
                });
            }

            @Override
            protected  void updateItem(Void item , boolean empty){
                super.updateItem(item,empty);

                if(empty){
                    setGraphic(null);
                }else{
                    setGraphic(hbox);
                }
            }
        });
    }

    //Chama as funções dos Botões
    private void editarProduto(ProdutoResponseDTO produto) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/pauloricardo/frontend_estoque/view/produto/editar-produto.fxml")
            );

            Parent root = loader.load();

            EditarProduto controller = loader.getController();
            controller.setProduto(produto);

            Stage stage = new Stage();
            stage.setTitle("Sistema de Gestão de Estoque  - Editar Produto");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            //Recarrega Produtos
            carregarProdutos();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void excluirProduto(ProdutoResponseDTO produto) {
        try{
            System.out.println(produto.getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação para Excluir Produto");
            alert.setHeaderText("Deseja Excluir o Produto - ID: "+produto.getId() + " Nome: " + produto.getNome());
            alert.setContentText("Deseja Continuar? ");

            Optional<ButtonType> resultado = alert.showAndWait();

            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                excluirProdutoAPI(produto.getId()); //Função da API para Excluir o Produto
            }else {
                mostrarAlerta("Cancelamento","Cliente Cancelou a Exclusão do Produto");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void excluirProdutoAPI(Integer id){

        try{
            System.out.println(Session.getToken());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8085/produto/" +id))
                    .header("Authorization", "Bearer " + Session.getToken())
                    .DELETE()
                    .build();

            System.out.println(request);

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<Void> response =
                    client.send(request,HttpResponse.BodyHandlers.discarding());

            System.out.println(response);

            if(response.statusCode() == 200){
                mostrarAlerta("Sucesso","Produto Excluido com Sucesso");
                carregarProdutos(); //Lista todos os Produtos Novamente

            }else {

                mostrarAlerta("Erro","Erro ao Exlcuir o Produto + ");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
