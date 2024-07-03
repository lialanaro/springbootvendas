package io.github.lialanaro.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AtualizacaoStatusPedidoDto {
    private String novoStatus;
}
