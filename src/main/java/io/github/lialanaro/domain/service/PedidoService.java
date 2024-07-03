package io.github.lialanaro.domain.service;

import io.github.lialanaro.domain.entity.Pedido;
import io.github.lialanaro.domain.entity.enums.StatusPedido;
import io.github.lialanaro.rest.dto.PedidoDto;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar (PedidoDto dto);
    Optional<Pedido> obterPedido (Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
