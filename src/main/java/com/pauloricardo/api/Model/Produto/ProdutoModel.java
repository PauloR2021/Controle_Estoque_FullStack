package com.pauloricardo.api.Model.Produto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter @Setter
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo_barras",unique = true,length = 50 )
    private String codigo_barras;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoqueMinimo = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (ativo == null) ativo = true;
        if (quantidade == null) quantidade = 0;
        if (estoqueMinimo == null) estoqueMinimo = 0;
    }

    public ProdutoModel() {
    }

    public ProdutoModel(Integer id, String nome, String codigo_barras, Integer quantidade, Integer estoqueMinimo, BigDecimal preco, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.codigo_barras = codigo_barras;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.preco = preco;
        this.ativo = ativo;
    }
}
