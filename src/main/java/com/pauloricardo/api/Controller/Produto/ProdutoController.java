package com.pauloricardo.api.Controller.Produto;


import com.pauloricardo.api.DTO.Produto.ProdutoRequestDTO;
import com.pauloricardo.api.DTO.Produto.ProdutoResponseDTO;
import com.pauloricardo.api.Infra.Security.SecurityConfiguration;
import com.pauloricardo.api.Service.Produto.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Rota para criar um Produto", description = "Criar Produto")
    @ApiResponse(responseCode ="201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode ="401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500",description = "Erro no servidor")
    public ResponseEntity<ProdutoResponseDTO> crearProduto(@RequestBody @Valid  ProdutoRequestDTO dto){

        ProdutoResponseDTO produto = produtoService.criarProduto(dto);

        return ResponseEntity.ok(produto);

    }

    @GetMapping
    @Operation(summary = "Rota para devolver as info dos Produtos ", description = "Devolve as informações dos Produtos")
    @ApiResponse(responseCode ="200", description = "Retorno realizado com sucesso")
    @ApiResponse(responseCode ="401", description = "Usuário não Autenticado")
    @ApiResponse(responseCode = "500",description = "Erro no servidor")
    public ResponseEntity<List<ProdutoResponseDTO>> byFindAll(){
        return ResponseEntity.ok(produtoService.findByAll());

    }

    @PutMapping("/{id}")
    @Operation(summary = "Rota para alterar as informações do Produto", description = "Consegue realizar update nos Produtos")
    @ApiResponse(responseCode ="200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode ="204", description = "Produto atualizado com sucesso mas sem retorno")
    @ApiResponse(responseCode ="401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500",description = "Erro no servidor")
    public ResponseEntity<ProdutoResponseDTO> editarProduto(
            @PathVariable Integer id ,
            @RequestBody @Valid ProdutoRequestDTO dto)
    {
        ProdutoResponseDTO response = produtoService.editarProduto(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Rota para Deletar Produto", description = "Consegue realizar Delete nos Produtos")
    @ApiResponse(responseCode ="200", description = "Produto Deletado com sucesso")
    @ApiResponse(responseCode ="204", description = "Produto Deletado com sucesso mas sem retorno")
    @ApiResponse(responseCode ="401", description = "Usuário não autenticado")
    @ApiResponse(responseCode = "500",description = "Erro no servidor")
    public ResponseEntity<String> deletarProduto(@PathVariable Integer id)
    {
        produtoService.excluirProduto(id);
       return ResponseEntity.ok("Produto Excluido com Sucesso");
    }


}
