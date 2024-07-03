package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.ItemPedido;
import io.github.lialanaro.domain.entity.Pedido;
import io.github.lialanaro.domain.entity.enums.StatusPedido;
import io.github.lialanaro.domain.service.PedidoService;
import io.github.lialanaro.rest.dto.AtualizacaoStatusPedidoDto;
import io.github.lialanaro.rest.dto.InformacoesItemPedidoDto;
import io.github.lialanaro.rest.dto.InformacoesPedidoDto;
import io.github.lialanaro.rest.dto.PedidoDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.lialanaro.domain.entity.helper.Constants.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedido")
@Api(API_PEDIDO)
public class PedidoController {
    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(PEDIDO_SALVO)
    @ApiResponses({
            @ApiResponse(code = 201,message = PEDIDO_EFETUADO),
            @ApiResponse(code = 400,message = CODIGO_INVALIDO)

    })
    public Integer save(@RequestBody @Valid PedidoDto dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();

    }

    @GetMapping("{id}")
    @ApiOperation(PROCURAR_PEDIDO_ID)
    @ApiResponses({
            @ApiResponse(code = 201,message = PEDIDO_ENCONTRADO),
            @ApiResponse(code = 404,message = PEDIDO_N_ENCONTRADO)

    })
    public InformacoesPedidoDto getById(@PathVariable @ApiParam(PEDIDO_ID)  Integer id) {
        return service.obterPedido(id)
                .map(p-> conveter(p)).orElseThrow(()->
                        new ResponseStatusException(NOT_FOUND, PEDIDO_N_ENCONTRADO));

    }

    private InformacoesPedidoDto conveter (Pedido pedido){
       return InformacoesPedidoDto
                .builder()
                .codigo(pedido.getId())
                .data(pedido.getDataPedido().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();

    }
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(ATUALIZAR_PEDIDO)
    @ApiResponses({
            @ApiResponse(code = 201,message = PEDIDO_ATUALIZADO),
            @ApiResponse(code = 204,message = "")

    })
    public void updateStatus(
            @PathVariable  @ApiParam("ID do pedido") Integer id,
            @RequestBody AtualizacaoStatusPedidoDto dto){
        String novoStatus =dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));


    }

    private List<InformacoesItemPedidoDto> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacoesItemPedidoDto
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }




}
