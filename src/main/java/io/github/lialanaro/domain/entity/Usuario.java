package io.github.lialanaro.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static io.github.lialanaro.domain.entity.helper.Constants.LOGIN_OBRIGATORIO;
import static io.github.lialanaro.domain.entity.helper.Constants.SENHA_OBRIGATORIO;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty(message = LOGIN_OBRIGATORIO)
    private String login;

    @Column
    @NotEmpty(message = SENHA_OBRIGATORIO)
    private String senha;

    @Column
    private boolean admin;
}
