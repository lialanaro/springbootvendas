package io.github.lialanaro;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository cliente){
        return args -> {
            System.out.println("Salvando clientes");
            cliente.salvar(new Cliente("Dougllas"));
            cliente.salvar(new Cliente("Outro Cliente"));

            List<Cliente> todosClientes = cliente.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                cliente.atualizar(c);
            });

            todosClientes = cliente.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando clientes");
            cliente.buscarPorNome("Cli").forEach(System.out::println);

//            System.out.println("deletando clientes");
//            clientes.obterTodos().forEach(c -> {
//                clientes.deletar(c);
//            });

            todosClientes = cliente.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{

                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
