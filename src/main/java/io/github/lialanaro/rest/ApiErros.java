package io.github.lialanaro.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErros {
    private List<String> erros;

    public ApiErros (String mensagemErro) {
        this.erros = Collections.singletonList(mensagemErro);
    }

}
