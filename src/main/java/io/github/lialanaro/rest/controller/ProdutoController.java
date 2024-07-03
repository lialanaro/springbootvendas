package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Produto;
import io.github.lialanaro.domain.repository.ProdutoRepository;
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
@RequestMapping("/api/produto")
@Api(API_PRODUTOS)
public class ProdutoController {
    @Autowired
    ProdutoRepository repository;

    @GetMapping("{id}")
    @ApiOperation(PESQUISAR_PRODUTO_ID)
    @ApiResponses({
            @ApiResponse(code = 201,message = PRODUTO_ENCONTRADO),
            @ApiResponse(code = 404,message = PRODUTO_N_ENCONTRADO)

    })
    public Produto findById(@PathVariable @ApiParam(PRODUTO_ID) Integer id){
        Optional<Produto> produto = repository.findById(id);
        return produto.orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND,PRODUTO_N_ENCONTRADO));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(SALVAR_PRODUTO)
    @ApiResponses({
            @ApiResponse(code = 201,message = PRODUTO_SALVO),
            @ApiResponse(code = 400,message = PARAMETROS_INCORRETOS)

    })
    public Produto save (@RequestBody @Valid Produto produto){
        Produto produtoSalvo = repository.save(produto);
        return produto;
    }

    @DeleteMapping("{id}")
    @ApiOperation(DELETAR_PRODUTO)
    @ApiResponses({
            @ApiResponse(code = 200,message = PRODUTO_DELETADO),
            @ApiResponse(code = 404,message = PRODUTO_N_ENCONTRADO)

    })
    public Produto delete (@PathVariable @ApiParam(PRODUTO_ID) Integer id){
        Optional<Produto> produto = repository.findById(id);
        produto.ifPresent(x-> repository.delete(x));
        return produto.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,PRODUTO_N_ENCONTRADO));
    }

    @PutMapping("{id}")
    @ApiOperation(ATUALIZAR_PRODUTO)
    @ApiResponses({
            @ApiResponse(code = 200,message = PRODUTO_ATUALIZADO),
            @ApiResponse(code = 404,message = PRODUTO_N_ENCONTRADO)

    })
    public Produto update (@PathVariable @ApiParam(PRODUTO_ID) Integer id,
                           @RequestBody @Valid Produto produto){
        Optional<Produto> produtoAtualizado = repository.findById(id);
        produtoAtualizado.ifPresent(x->{
                    produto.setId(id);
                    repository.save(produto);
                });
        return produtoAtualizado.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUTO_N_ENCONTRADO));
     }

     @GetMapping
     @ApiOperation(PESQUISAR_PRODUTO)
     @ApiResponses({
             @ApiResponse(code = 200,message = PRODUTO_ENCONTRADO),
             @ApiResponse(code = 404,message = PRODUTO_N_ENCONTRADO)

     })
     public List<Produto> findAll(Produto filter){
         ExampleMatcher matcher = ExampleMatcher
                 .matching()
                 .withIgnoreCase()
                 .withStringMatcher(
                         ExampleMatcher.StringMatcher.CONTAINING);

         Example<Produto> example = Example.of(filter,matcher);
         return repository.findAll(example);

     }
}
