package io.github.lialanaro.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

import static io.github.lialanaro.domain.entity.helper.Constants.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;


    @Column(name="nome", length=100)
    @NotEmpty(message = NOME_OBRIGATORIO)
    private String nome;

    @JsonProperty("cpf")
    @NotEmpty(message = CPF_OBRIGATORIO)
    @CPF(message = CPF_INVALIDO)
    @Column(name="cpf", length=14)
    private String cpf;


    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    public Cliente(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
