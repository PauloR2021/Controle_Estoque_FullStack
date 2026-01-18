package com.pauloricardo.api.DTO;

public record RegistrarDTO(
        String nome,
        String username,
        String password,
        Boolean ativo
) {
}
