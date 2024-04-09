package io.github.lialanaro.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedido {
    private Integer id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;


}
