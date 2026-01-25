package com.pauloricardo.api.DTO.Usuario;

public record ResetSenhaDTO(
        String email,
        String code,
        String novaSenha
) {
}
