package com.pauloricardo.api.Model.Movimentacao;

import com.pauloricardo.api.Model.Produto.ProdutoModel;
import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
@Getter @Setter
public class MovimentacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id",nullable = false)
    private ProdutoModel produtoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioId;

    @Column(nullable = false, length = 20)
    private String tipo; //ENTRADA /SAIDA

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }

    public MovimentacaoModel(Integer id, ProdutoModel produtoId, UsuarioModel usuarioId, String tipo, Integer quantidade, LocalDateTime dataHora) {
        this.id = id;
        this.produtoId = produtoId;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
    }

    public MovimentacaoModel() {
    }
}
