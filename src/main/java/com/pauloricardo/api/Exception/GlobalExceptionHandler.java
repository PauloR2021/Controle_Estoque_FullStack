package com.pauloricardo.api.Exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import kotlin.io.AccessDeniedException;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //========================
    //Tratando Erro de Json
    //========================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErroDTO> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(),
                        "Invalid request Body",
                        request.getRequestURI()
                ));
    }

    //========================
    //Recurso não Encontrado
    //========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErroDTO> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.name(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    //========================
    //Username Inválido
    //========================

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErroDTO> handleUsernameNotFound(
            UsernameNotFoundException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.name(),
                        "Invalid Username Or Password",
                        request.getRequestURI()
                ));
    }

    //========================
    //Credencias Inválidas Login
    //========================
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErroDTO> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.name(),
                        "Invalid Username Or Password",
                        request.getRequestURI()
                ));
    }

    //========================
    //Acesso Negado (403)
    //========================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErroDTO> handleAccessDanied(
            AccessDeniedException exception,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.name(),
                        "You do Not Permission To Access This Resource",
                        request.getRequestURI()
                ));
    }

    //========================
    //Validação de Campos
    //========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErroDTO> handleValidationError(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " +err.getDefaultMessage())
                .orElse("Validation Error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(),
                        message,
                        request.getRequestURI()
                ));
    }

    //========================
    //Erro de Dados Vazio
    //========================
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErroDTO> handlerNotFound (
            EntityNotFoundException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.name(),
                        "Entity Not Foun",
                        request.getRequestURI()
                ));
    }


    //========================
    //Erro na Requisição HTTP
    //========================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErroDTO> handlerRequestMethod(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ){

        String metodos = ex.getSupportedHttpMethods() != null
                ? ex.getSupportedHttpMethods()
                .stream()
                .map(HttpMethod::name)
                .collect(Collectors.joining(", "))
                : "N/A ";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiErroDTO(
                        LocalDateTime.now(),
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        HttpStatus.METHOD_NOT_ALLOWED.name(),
                        "Error Method Http. Use Method: "+metodos,
                        request.getRequestURI()
                ));
    }




}
