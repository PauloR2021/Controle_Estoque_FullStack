package com.pauloricardo.frontend_estoque.DTO.Produto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class ProdutoResponseDTO {
    private  Integer id;
    private  String nome;
    private  String codigoBarras;
    private  BigDecimal preco;
    private  Integer quantidade;
    private  Integer quantidadeMinima;
    private  Boolean ativo;
    private  LocalDateTime criacao;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(
            Integer id,
            String nome,
            String codigoBarras,
            BigDecimal preco,
            Integer quantidade,
            Integer quantidadeMinima,
            Boolean ativo,
            LocalDateTime criacao
    ) {
        this.id = id;
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.ativo = ativo;
        this.criacao = criacao;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getCodigoBarras() { return codigoBarras; }
    public BigDecimal getPreco() { return preco; }
    public Integer getQuantidade() { return quantidade; }
    public Integer getQuantidadeMinima() { return quantidadeMinima; }
    public Boolean getAtivo() { return ativo; }
    public LocalDateTime getCriacao() { return criacao; }
}
