package io.github.lialanaro.domain.service.impl;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.entity.ItemPedido;
import io.github.lialanaro.domain.entity.Pedido;
import io.github.lialanaro.domain.entity.Produto;
import io.github.lialanaro.domain.entity.enums.StatusPedido;
import io.github.lialanaro.domain.repository.ClienteRepository;
import io.github.lialanaro.domain.repository.ItemPedidoRepository;
import io.github.lialanaro.domain.repository.PedidoRepository;
import io.github.lialanaro.domain.repository.ProdutoRepository;
import io.github.lialanaro.domain.service.PedidoService;
import io.github.lialanaro.exception.PedidoNaoEncontradoException;
import io.github.lialanaro.exception.RegraNegocioException;
import io.github.lialanaro.rest.dto.ItemPedidoDto;
import io.github.lialanaro.rest.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.lialanaro.domain.entity.helper.Constants.*;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDto dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow
                (()-> new RegraNegocioException(CODIGO_CLIENTE));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = conveterItems(pedido, dto.getItems());
        pedido.setItens(itemPedidos);
        itemPedidos.forEach(itemPedidoRepository::save);
        pedidoRepository.save(pedido);



        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedido(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(PedidoNaoEncontradoException::new);

    }


    private List<ItemPedido> conveterItems (Pedido pedido, List<ItemPedidoDto> items) {
        if(items.isEmpty()){
            throw  new RegraNegocioException(PEDIDO_ERRO);
        }
        return items
                .stream()
                .map(dto ->{

                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                            .orElseThrow(()-> new RegraNegocioException(PRODUTO_ERRO + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return  itemPedido;
                }).collect(Collectors.toList());

    }
}
