package br.com.cantina.service;

import br.com.cantina.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Map<String, Object>> listarTodos() {
        return produtoRepository.listarTodos();
    }

    public Map<String, Object> buscarPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }

    
    public int cadastrar(String nome, double preco, int estoque) {
        validarDadosProduto(nome, preco);

        if (estoque < 0) {
            throw new IllegalArgumentException(
                "O estoque não pode ser negativo."
            );
        }

        return produtoRepository.cadastrar(nome, preco, estoque);
    }

    public int atualizar(int id, String nome, double preco) {
        validarDadosProduto(nome, preco);

        return produtoRepository.atualizar(id, nome, preco);
    }

    private void validarDadosProduto(String nome, double preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto é obrigatório."
            );
        }

        if (preco <= 0 || !Double.isFinite(preco)) {
            throw new IllegalArgumentException(
                "O preço deve ser maior que zero."
            );
        }
    }


    public void excluir(int id) {
        Map<String, Object> produto = buscarPorId(id);

        if (produto == null) {
            throw new IllegalArgumentException(
                "Produto não encontrado."
            );
        }

        if (produtoRepository.possuiHistorico(id)) {
            throw new IllegalArgumentException(
                "Não é possível excluir um produto com histórico de pedidos."
            );
        }

        produtoRepository.excluir(id);
    }
}