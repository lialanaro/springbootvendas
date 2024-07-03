package io.github.lialanaro.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static io.github.lialanaro.domain.entity.helper.Constants.DESCRICAO_OBRIGATORIO;
import static io.github.lialanaro.domain.entity.helper.Constants.PRECO_OBRIGATORIO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = DESCRICAO_OBRIGATORIO )
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = PRECO_OBRIGATORIO)
    private BigDecimal preco;

}
