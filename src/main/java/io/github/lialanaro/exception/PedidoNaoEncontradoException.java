package io.github.lialanaro.exception;

import static io.github.lialanaro.domain.entity.helper.Constants.PEDIDO_N_ENCONTRADO;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException() {
        super(PEDIDO_N_ENCONTRADO);
    }

}
