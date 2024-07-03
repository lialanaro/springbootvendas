package io.github.lialanaro.domain.repository;

import io.github.lialanaro.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto,Integer > {

}
