package br.com.cantina.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listarTodos() {
        String sql = """
            SELECT id, nome, email
            FROM clientes
            ORDER BY id
            """;

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> buscarPorId(int id) {
        String sql = """
            SELECT id, nome, email
            FROM clientes
            WHERE id = ?
            """;

        List<Map<String, Object>> resultado =
                jdbcTemplate.queryForList(sql, id);

        if (resultado.isEmpty()) {
            return null;
        }

        return resultado.get(0);
    }
}