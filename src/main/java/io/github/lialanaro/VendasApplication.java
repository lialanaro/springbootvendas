package io.github.lialanaro;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.entity.Pedido;
import io.github.lialanaro.domain.repository.ClienteRepository;
import io.github.lialanaro.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@SpringBootApplication
public class VendasApplication {
    @Bean
    public CommandLineRunner init(
            @Autowired ClienteRepository clientes,
            @Autowired PedidoRepository pedidos
    ) {
        return args -> {
            System.out.println("Salvando clientes");
            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));

            pedidos.save(p);
            pedidos.findByCliente(fulano).forEach(System.out::println);



        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }


}
