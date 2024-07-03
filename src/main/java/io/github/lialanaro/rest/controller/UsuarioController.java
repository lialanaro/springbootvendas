package io.github.lialanaro.rest.controller;

import io.github.lialanaro.domain.entity.Usuario;
import io.github.lialanaro.domain.service.impl.UsuarioServiceImpl;
import io.github.lialanaro.exception.SenhaInvalidaException;
import io.github.lialanaro.rest.dto.CredenciaisDto;
import io.github.lialanaro.rest.dto.TokenDto;
import io.github.lialanaro.security.jwt.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static io.github.lialanaro.domain.entity.helper.Constants.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Api(API_USUARIO)
public class UsuarioController {
    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(CADASTRAR_USUARIO)
    @ApiResponses({
            @ApiResponse(code = 201,message = USUARIO_SALVO)

    })
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada =passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);

    }
    @PostMapping("/auth")
    @ApiOperation(AUTENTICAR_USUARIO)
    @ApiResponses({
            @ApiResponse(code = 201,message = USUARIO_SALVO),
            @ApiResponse(code = 401,message = DADOS_INCORRETOS)

    })
    public TokenDto autenticar (@RequestBody CredenciaisDto credenciaisDto){
        try{
            Usuario build = Usuario.builder()
                    .login(credenciaisDto.getLogin())
                    .senha(credenciaisDto.getSenha()).build();
            UserDetails autenticar = usuarioService.autenticar(build);
            String token = jwtService.gerarToken(build);
            return new TokenDto(build.getLogin(),token);


        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());


      }
    }
}
