package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Produto;
import io.github.lialanaro.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProdutoControllerTest {
    @Autowired
    ProdutoController controller;

    @Autowired
    ProdutoRepository repository;


    @BeforeAll
    void dados(){
        Produto produto1 = new Produto();
        produto1.setDescricao("impressora");
        produto1.setPreco(BigDecimal.valueOf(10.00));

        Produto produto2 = new Produto();
        produto2.setDescricao("mouse");
        produto2.setPreco(BigDecimal.valueOf(20.00));

        Produto produto3 = new Produto();
        produto3.setDescricao("monitor");
        produto3.setPreco(BigDecimal.valueOf(5.00));

        Produto produto4 = new Produto();
        produto4.setDescricao("teclado");
        produto4.setPreco(BigDecimal.valueOf(15.00));

        repository.save(produto1);
        repository.save(produto2);
        repository.save(produto3);
        repository.save(produto4);

    }

    @Test
    void findById() {
        Produto produto= controller.findById(1);
        assertEquals(1,produto.getId());
        assertEquals("impressora",produto.getDescricao());
        assertEquals(0,produto.getPreco().compareTo(BigDecimal.valueOf(10.00)));
    }

    @Test
    void save() {
        Produto produto = new Produto();
        produto.setPreco(BigDecimal.valueOf(10.00));
        produto.setDescricao("fominha");
        controller.save(produto);
        assertEquals(5, produto.getId());

    }

    @Test
    void delete() {
        controller.delete(3);
        assertThrows(ResponseStatusException.class, ()-> controller.findById(3));
        assertThrows(ResponseStatusException.class,()-> controller.delete(200));
    }

    @Test
    void update() {
        Produto produto = new Produto();
        produto.setDescricao("controle ps3");
        produto.setPreco(BigDecimal.valueOf(100.00));
        controller.update(4,produto);
        Produto produto1 = controller.findById(4);
        assertEquals(produto.getDescricao(),produto1.getDescricao());
        assertEquals(0,produto.getPreco().compareTo(produto1.getPreco()));
        assertThrows(ResponseStatusException.class,()->controller.update(200,produto));

    }

    @Test
    void zfindAll() {
        List<Produto> lista = controller.findAll(new Produto());
        lista.forEach(System.out::println);
        assertEquals(4,lista.size());

    }
}