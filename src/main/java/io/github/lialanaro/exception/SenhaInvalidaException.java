package io.github.lialanaro.exception;

import static io.github.lialanaro.domain.entity.helper.Constants.DADOS_INCORRETOS;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super(DADOS_INCORRETOS);
    }
}
