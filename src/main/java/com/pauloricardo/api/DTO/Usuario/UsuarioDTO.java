package com.pauloricardo.api.DTO.Usuario;

import com.pauloricardo.api.Model.Usuario.UsuarioRole;

public record UsuarioDTO(
        String nome,
        String username,
        String password,
        UsuarioRole role,
        Boolean ativo,
        String email,
        String codigoVerificacao
) {
}
