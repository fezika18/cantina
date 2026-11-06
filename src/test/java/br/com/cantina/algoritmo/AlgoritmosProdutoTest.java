
package br.com.cantina.algoritmo;

import br.com.cantina.domain.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgoritmosProdutoTest {

    private final AlgoritmosProduto algoritmos = new AlgoritmosProduto();

    private List<Produto> criarProdutos() {
        List<Produto> produtos = new ArrayList<>();

        produtos.add(new Produto(3, "Suco", new BigDecimal("6.00"), 10));
        produtos.add(new Produto(1, "Cafe", new BigDecimal("4.50"), 20));
        produtos.add(new Produto(2, "Salgado", new BigDecimal("8.00"), 15));

        return produtos;
    }

    @Test
    void deveEncontrarProdutoNaBuscaLinear() {
        List<Produto> produtos = criarProdutos();

        Produto resultado = algoritmos.buscaLinear(produtos, 2);

        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("Salgado", resultado.getNome());
    }

    @Test
    void deveRetornarNuloQuandoProdutoNaoExistirNaBuscaLinear() {
        List<Produto> produtos = criarProdutos();

        Produto resultado = algoritmos.buscaLinear(produtos, 99);

        assertNull(resultado);
    }

    @Test
    void deveEncontrarProdutoNaBuscaBinaria() {
        List<Produto> produtos = algoritmos.bubbleSort(criarProdutos());

        Produto resultado = algoritmos.buscaBinaria(produtos, 2);

        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("Salgado", resultado.getNome());
    }

    @Test
    void deveOrdenarProdutosPorId() {
        List<Produto> produtos = criarProdutos();

        List<Produto> ordenados = algoritmos.bubbleSort(produtos);

        assertEquals(1, ordenados.get(0).getId());
        assertEquals(2, ordenados.get(1).getId());
        assertEquals(3, ordenados.get(2).getId());

        // A lista original deve permanecer inalterada.
        assertEquals(3, produtos.get(0).getId());
    }
}
