package br.com.cantina.service;

import br.com.cantina.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void criarPedido(int clienteId, int produtoId) {

        if (clienteId <= 0 || produtoId <= 0) {
            throw new IllegalArgumentException(
                "Os IDs do cliente e do produto devem ser positivos."
            );
        }

        if (!pedidoRepository.clienteExiste(clienteId)) {
            throw new IllegalArgumentException(
                "Cliente não encontrado."
            );
        }

        Map<String, Object> produto =
                pedidoRepository.buscarProdutoParaPedido(produtoId);

        if (produto == null) {
            throw new IllegalArgumentException(
                "Produto não encontrado."
            );
        }

        int estoque = ((Number) produto.get("estoque_disponivel"))
                .intValue();

        if (estoque <= 0) {
            throw new IllegalArgumentException(
                "Produto sem estoque disponível."
            );
        }

        double preco = ((Number) produto.get("preco")).doubleValue();

        int linhasAlteradas =
                pedidoRepository.descontarEstoque(produtoId);

        if (linhasAlteradas == 0) {
            throw new IllegalArgumentException(
                "Não foi possível reservar o produto. Verifique o estoque."
            );
        }

        pedidoRepository.inserirPedido(clienteId, produtoId, preco);
    }

    public List<Map<String, Object>> listarTodos() {
        return pedidoRepository.listarTodos();
    }

    public Map<String, Object> buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do pedido deve ser maior que zero."
            );
        }

        return pedidoRepository.buscarPorId(id);
    }

    @Transactional
    public void cancelarPedido(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do pedido deve ser maior que zero."
            );
        }

        Map<String, Object> pedido =
                pedidoRepository.buscarParaCancelamento(id);

        if (pedido == null) {
            throw new IllegalArgumentException(
                "Pedido não encontrado."
            );
        }

        String situacao = pedido.get("situacao").toString();

        if (!"ATIVO".equals(situacao)) {
            throw new IllegalArgumentException(
                "Este pedido já foi cancelado."
            );
        }

        int linhasAlteradas = pedidoRepository.cancelarPedido(id);

        if (linhasAlteradas == 0) {
            throw new IllegalArgumentException(
                "Não foi possível cancelar o pedido."
            );
        }

        int produtoId =
                ((Number) pedido.get("produto_id")).intValue();

        int linhasEstoque =
                pedidoRepository.devolverEstoque(produtoId);

        if (linhasEstoque == 0) {
            throw new IllegalStateException(
                "Não foi possível devolver o estoque."
            );
        }
    }
}