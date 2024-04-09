package io.github.lialanaro.domain.repository;

import io.github.lialanaro.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {
    private static  String SELECT_POR_NOME = "select * from cliente where nome like ?";
    private static String  INSERT = "insert into cliente (nome) values (?)";
    private static String  SELECT_ALL = "select * from cliente";
    private static String  UPDATE   = "update cliente set nome = ? where id = ?";

    private static String  DELETE = "delete from cliente where id = ?";

   @Autowired
     private JdbcTemplate jdbcTemplate;

   @Autowired
   private EntityManager entityManager;

    public Cliente salvar (Cliente cliente ){
        entityManager.persist(cliente);
        return cliente;
    }
    @Transactional
    public Cliente atualizar (Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente){
       entityManager.remove(cliente);

    }

    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);

    }

    public List<Cliente> buscarPorNome(String nome){
         return jdbcTemplate.query(SELECT_POR_NOME,new Object[]{"%" + nome + "%"},obterClientes());
    }

    public List<Cliente> obterTodos() {
        return jdbcTemplate.query(SELECT_ALL, obterClientes());
    }

    private static RowMapper<Cliente> obterClientes() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        };
    }

}
