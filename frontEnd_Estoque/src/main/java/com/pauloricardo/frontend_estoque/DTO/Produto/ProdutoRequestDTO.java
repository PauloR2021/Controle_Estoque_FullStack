package com.pauloricardo.frontend_estoque.DTO.Produto;

import java.math.BigDecimal;

public class ProdutoRequestDTO {

    String nome;
    String codigoBarras;
    Integer quantidade;
    Integer quantidadeMininma;
    BigDecimal preco;
    Boolean ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidadeMininma() {
        return quantidadeMininma;
    }

    public void setQuantidadeMininma(Integer quantidadeMininma) {
        this.quantidadeMininma = quantidadeMininma;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
