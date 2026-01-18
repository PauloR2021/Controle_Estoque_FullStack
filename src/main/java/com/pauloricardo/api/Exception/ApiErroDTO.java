package com.pauloricardo.api.Exception;

import java.time.LocalDateTime;

public record ApiErroDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
