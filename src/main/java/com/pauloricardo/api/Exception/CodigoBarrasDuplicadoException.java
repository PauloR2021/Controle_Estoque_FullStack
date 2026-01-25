package com.pauloricardo.api.Exception;

public class CodigoBarrasDuplicadoException extends RuntimeException  {
    public CodigoBarrasDuplicadoException() {
        super("Código de barras já cadastrado");
    }
}
