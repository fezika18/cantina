package br.com.cantina.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PedidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> buscarProdutoParaPedido(int produtoId) {
        String sql = """
            SELECT id, nome, preco, estoque_disponivel
            FROM produtos
            WHERE id = ?
            """;

        List<Map<String, Object>> resultado =
                jdbcTemplate.queryForList(sql, produtoId);

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }

    public boolean clienteExiste(int clienteId) {
        String sql = """
            SELECT COUNT(*)
            FROM clientes
            WHERE id = ?
            """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql, Integer.class, clienteId
        );

        return quantidade != null && quantidade > 0;
    }
    public int descontarEstoque(int produtoId) {
        String sql = """
            UPDATE produtos
            SET estoque_disponivel = estoque_disponivel - 1
            WHERE id = ?
            AND estoque_disponivel > 0
            """;

        return jdbcTemplate.update(sql, produtoId);
    }

    public int inserirPedido(
            int clienteId, int produtoId, double preco) {

        String sql = """
            INSERT INTO pedidos
                (cliente_id, produto_id, preco_registrado,
                data_registro, situacao)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP, 'ATIVO')
            """;

        return jdbcTemplate.update(
                sql, clienteId, produtoId, preco
        );
    }

    public List<Map<String, Object>> listarTodos() {
        String sql = """
            SELECT p.id, p.cliente_id, c.nome AS cliente,
                p.produto_id, pr.nome AS produto,
                p.preco_registrado, p.data_registro, p.situacao
            FROM pedidos p
            JOIN clientes c ON p.cliente_id = c.id
            JOIN produtos pr ON p.produto_id = pr.id
            ORDER BY p.id DESC
            """;

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> buscarPorId(int id) {
        String sql = """
            SELECT p.id, p.cliente_id, c.nome AS cliente,
                p.produto_id, pr.nome AS produto,
                p.preco_registrado, p.data_registro, p.situacao
            FROM pedidos p
            JOIN clientes c ON p.cliente_id = c.id
            JOIN produtos pr ON p.produto_id = pr.id
            WHERE p.id = ?
            """;

        List<Map<String, Object>> resultado =
                jdbcTemplate.queryForList(sql, id);

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }

    public Map<String, Object> buscarParaCancelamento(int id) {
        String sql = """
            SELECT id, produto_id, situacao
            FROM pedidos
            WHERE id = ?
            FOR UPDATE
            """;

        List<Map<String, Object>> resultado =
                jdbcTemplate.queryForList(sql, id);

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }

    public int cancelarPedido(int id) {
        String sql = """
            UPDATE pedidos
            SET situacao = 'CANCELADO'
            WHERE id = ?
            AND situacao = 'ATIVO'
            """;

        return jdbcTemplate.update(sql, id);
    }

    public int devolverEstoque(int produtoId) {
        String sql = """
            UPDATE produtos
            SET estoque_disponivel = estoque_disponivel + 1
            WHERE id = ?
            """;

        return jdbcTemplate.update(sql, produtoId);
    }
}