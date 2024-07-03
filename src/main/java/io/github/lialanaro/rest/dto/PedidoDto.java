package io.github.lialanaro.rest.dto;

import io.github.lialanaro.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import static io.github.lialanaro.domain.entity.helper.Constants.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDto {
    @NotNull(message = INFORME_CODIGO)
    private Integer cliente;
    @NotNull(message = CAMPO_TOTAL_OBRIGATORIO)
    private BigDecimal total;
    @NotEmptyList(message = PEDIDO_ERRO)
    private List<ItemPedidoDto> items;
}
