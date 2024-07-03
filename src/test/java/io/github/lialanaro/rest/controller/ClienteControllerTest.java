package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ClienteControllerTest {
    @Autowired
    ClienteRepository repository;

    @Autowired
    ClienteController controller;

    @BeforeAll
     void beforeAll(){
        Cliente cliente1 = new Cliente();
        cliente1.setCpf("118.282.510-97");
        cliente1.setNome("Anderson");

        Cliente cliente2 = new Cliente();
        cliente2.setCpf("310.097.820-02");
        cliente2.setNome("Lia");

        Cliente clienteDel = new Cliente();
        clienteDel.setNome("ribaldao");
        clienteDel.setCpf("162.531.670-49");

        Cliente clienteUpdt = new Cliente();
        clienteUpdt.setCpf("881.126.190-26");
        clienteUpdt.setNome("Andershow");

        repository.save(cliente1);
        repository.save(cliente2);
        repository.save(clienteDel);
        repository.save(clienteUpdt);

    }
    @Test
    void getClienteById() {
        Cliente clienteById = controller.getClienteById(1);
        assertEquals(1,clienteById.getId());
        assertEquals("Anderson",clienteById.getNome());
        assertEquals("118.282.510-97",clienteById.getCpf());


    }

    @Test
    void save() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ander");
        cliente.setCpf("410.302.900-59");
        controller.save(cliente);
        assertEquals(5,cliente.getId());
    }

    @Test
    void delete() {
        controller.delete(3);
        assertThrows(ResponseStatusException.class, ()-> controller.getClienteById(3));
        assertThrows(ResponseStatusException.class,()-> controller.delete(200));


    }

    @Test
    void update() {
        Cliente cliente = new Cliente();
        cliente.setCpf("908.476.330-04");
        cliente.setNome("nathalinha");
        controller.update(4,cliente);
        Cliente cliente2 = controller.getClienteById(4);
        assertEquals(cliente.getNome(),cliente2.getNome());
        assertEquals(cliente.getCpf(),cliente2.getCpf());
        assertThrows(ResponseStatusException.class,()->controller.update(200,cliente));

    }

    @Test
    void find() {
        Cliente cliente = new Cliente();
        cliente.setNome("lia");
        List <Cliente> find = controller.find(cliente);
        assertEquals(1,find.size());
        assertEquals(2,find.get(0).getId());
        assert(cliente.getNome().equalsIgnoreCase(find.get(0).getNome()));
        assertEquals("310.097.820-02", find.get(0).getCpf());



    }
}