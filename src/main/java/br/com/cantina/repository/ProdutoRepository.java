
package br.com.cantina.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listarTodos() {
        String sql = """
            SELECT id, nome, preco, estoque_disponivel
            FROM produtos
            ORDER BY id
            """;

        return jdbcTemplate.queryForList(sql);
        
    }
    
    public Map<String, Object> buscarPorId(int id) {
        String sql = """
            SELECT id, nome, preco, estoque_disponivel
            FROM produtos
            WHERE id = ?
            """;

        List<Map<String, Object>> resultado =
            jdbcTemplate.queryForList(sql, id);

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }
    
    public int cadastrar(String nome, double preco, int estoque) {
        String sql = """
            INSERT INTO produtos (nome, preco, estoque_disponivel)
            VALUES (?, ?, ?)
            """;

        return jdbcTemplate.update(sql, nome, preco, estoque);
    }

    public int atualizar(int id, String nome, double preco) {
        String sql = """
            UPDATE produtos
            SET nome = ?, preco = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(sql, nome, preco, id);
    }

    public boolean possuiHistorico(int id) {
        String sql = """
            SELECT COUNT(*)
            FROM pedidos
            WHERE produto_id = ?
            """;

        Integer quantidade = jdbcTemplate.queryForObject(
            sql, Integer.class, id
        );

        return quantidade != null && quantidade > 0;
    }

    public int excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    
}
