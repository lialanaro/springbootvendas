package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Cliente;
import io.github.lialanaro.domain.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static io.github.lialanaro.domain.entity.helper.Constants.*;

@RestController
@RequestMapping("/api/cliente")
@Api(API_CLIENTE)
public class ClienteController {
    @Autowired
    private ClienteRepository repository;

    @GetMapping("{id}")
    @ApiOperation(OBTER_DADOS_CLIENTE)
    @ApiResponses({
            @ApiResponse(code = 200,message = CLIENTE_ENCONTRADO),
            @ApiResponse(code = 404,message = CLIENTE_N_ENCONTRADO)

    })
    public Cliente getClienteById(@PathVariable @ApiParam(CLIENTE_ID)Integer id){
        Optional<Cliente> cliente =  repository.findById(id);
        return cliente.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,CLIENTE_N_ENCONTRADO));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(SALVAR_CLIENTE)
    @ApiResponses({
            @ApiResponse(code = 201,message = CLIENTE_SALVO),
            @ApiResponse(code = 400,message = PARAMETROS_INCORRETOS)

    })
    public Cliente save(@RequestBody @Valid Cliente cliente){
        Cliente clienteSalvo = repository.save(cliente);
        return cliente;

    }
    @DeleteMapping("{id}")
    @ApiOperation(DELETAR_CLIENTE)
    @ApiResponses({
            @ApiResponse(code = 200,message = CLIENTE_DELETADO),
            @ApiResponse(code = 404,message = CLIENTE_N_ENCONTRADO)

    })
    public Cliente delete (@PathVariable @ApiParam(CLIENTE_ID)Integer id){
        Optional<Cliente> cliente = repository.findById(id);
        cliente.ifPresent(x->repository.delete(x));
        // cliente.ifPresent(repository::delete);
        return cliente.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_N_ENCONTRADO));

    }

    @PutMapping("{id}")
    @ApiOperation(ATUALIZAR_CLIENTE)
    @ApiResponses({
            @ApiResponse(code = 200,message = CLIENTE_ATUALIZADO),
            @ApiResponse(code = 404,message = CLIENTE_N_ENCONTRADO)

    })
    public Cliente update (@PathVariable @ApiParam(CLIENTE_ID)Integer id,
                                           @RequestBody Cliente cliente){
        Optional<Cliente> clienteAtualizado = repository.findById(id);
         clienteAtualizado
                .ifPresent(x-> {
                    cliente.setId(id);
                    repository.save(cliente);
                });
         return clienteAtualizado.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,CLIENTE_N_ENCONTRADO));

    }

    @GetMapping
    @ApiOperation(PESQUISAR_CLIENTE)
    @ApiResponses({
            @ApiResponse(code = 200,message = CLIENTE_ENCONTRADO),
            @ApiResponse(code = 404,message = CLIENTE_N_ENCONTRADO)

    })
    public List<Cliente> find (Cliente filter){
        //@JsonProperties não da match no filtro
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example<Cliente> example = Example.of(filter,matcher);
        return repository.findAll(example);

    }


}
