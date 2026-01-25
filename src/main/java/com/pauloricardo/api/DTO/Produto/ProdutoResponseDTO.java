package com.pauloricardo.api.DTO.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Integer id,
        String nome,
        String codigoBarras,
        Integer quantidade,
        Integer quantidadeMinima,
        BigDecimal preco,
        Boolean ativo,
        LocalDateTime criacao

) {
}
