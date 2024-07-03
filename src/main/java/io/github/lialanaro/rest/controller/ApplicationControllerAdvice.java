package io.github.lialanaro.rest.controller;

import io.github.lialanaro.exception.PedidoNaoEncontradoException;
import io.github.lialanaro.exception.RegraNegocioException;
import io.github.lialanaro.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErros(mensagemErro);
    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handlerNotfoundException(PedidoNaoEncontradoException ex){
        return new ApiErros(ex.getMessage());

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handlrMethodNotValidException(MethodArgumentNotValidException ex){
         return new ApiErros(ex.getBindingResult()
                 .getAllErrors()
                 .stream()
                 .map(erro -> erro.getDefaultMessage()).collect(Collectors.toList()));

    }

}
