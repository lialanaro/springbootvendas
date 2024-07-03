package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.entity.Produto;
import io.github.lialanaro.domain.repository.ClienteRepository;
import io.github.lialanaro.domain.repository.ItemPedidoRepository;
import io.github.lialanaro.domain.repository.PedidoRepository;
import io.github.lialanaro.domain.repository.ProdutoRepository;
import io.github.lialanaro.rest.dto.AtualizacaoStatusPedidoDto;
import io.github.lialanaro.rest.dto.InformacoesPedidoDto;
import io.github.lialanaro.rest.dto.ItemPedidoDto;
import io.github.lialanaro.rest.dto.PedidoDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional

class PedidoControllerTest {

    @Autowired
    PedidoController controller;

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;
    @BeforeAll
    void before(){

        Cliente cliente1 = new Cliente();
        cliente1.setCpf("118.282.510-97");
        cliente1.setNome("Anderson");
        cliente1.setId(1);
        clienteRepository.save(cliente1);

        Produto produto1 = new Produto();
        produto1.setDescricao("impressora");
        produto1.setPreco(BigDecimal.valueOf(10.00));

        produtoRepository.save(produto1);

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setCliente(1);
        pedidoDto.setTotal(BigDecimal.valueOf(20.00));
        pedidoDto.setItems(Collections.singletonList(new ItemPedidoDto(2,2)));
        controller.save(pedidoDto);





    }
    @Test
    void save() {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setCliente(1);
        pedidoDto.setTotal(BigDecimal.valueOf(20.00));
        pedidoDto.setItems(Collections.singletonList(new ItemPedidoDto(2,2)));
        Integer saved = controller.save(pedidoDto);
        assertEquals(6,saved);

    }

    @Test
    void getById() {
        InformacoesPedidoDto pedido = controller.getById(4);
        System.out.println(pedido);
        assertEquals(4,pedido.getCodigo());
        assertEquals("118.282.510-97",pedido.getCpf());
        assertEquals("Anderson",pedido.getNomeCliente());
        assertEquals(0,pedido.getTotal().compareTo(BigDecimal.valueOf(20.00)));
        assertEquals(LocalDate.now().format(ofPattern("dd/MM/yyyy")), pedido.getData());
        assertEquals("REALIZADO",pedido.getStatus());
        assertEquals("impressora",pedido.getItens().get(0).getDescricaoProduto());
        assertEquals(2,pedido.getItens().get(0).getQuantidade());
        assertEquals(0,pedido.getItens().get(0).getPrecoUnitario().compareTo(BigDecimal.valueOf(10.00)));


    }

    @Test
    void updateStatus() {
        controller.updateStatus(4,new AtualizacaoStatusPedidoDto("CANCELADO"));
        InformacoesPedidoDto byId = controller.getById(4);
        assertEquals("CANCELADO",byId.getStatus());



    }
}