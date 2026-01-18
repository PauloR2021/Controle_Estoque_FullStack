package com.pauloricardo.api.DTO.Autenticacao;

public record RegistrarDTO(
        String nome,
        String username,
        String password,
        Boolean ativo
) {
}
