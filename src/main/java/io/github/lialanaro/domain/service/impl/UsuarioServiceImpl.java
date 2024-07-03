package io.github.lialanaro.domain.service.impl;

import io.github.lialanaro.domain.entity.Usuario;
import io.github.lialanaro.domain.repository.UsuarioRepository;
import io.github.lialanaro.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.github.lialanaro.domain.entity.helper.Constants.USUARIO_N_ENCONTRADO;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar (Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhaBate = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if(senhaBate){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(s)
                .orElseThrow(()-> new UsernameNotFoundException(USUARIO_N_ENCONTRADO));
        String[] roles = usuario.isAdmin()? new String[]{"ADMIN","USER"} : new String []{"USER"};
        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

    }
}
