
package br.com.cantina.service;

import br.com.cantina.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveRejeitarPedidoComClienteInexistente() {
        when(pedidoRepository.clienteExiste(99))
                .thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.criarPedido(99, 1)
        );

        verify(pedidoRepository).clienteExiste(99);
        verify(pedidoRepository, never())
                .buscarProdutoParaPedido(anyInt());
        verify(pedidoRepository, never())
                .descontarEstoque(anyInt());
        verify(pedidoRepository, never())
                .inserirPedido(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void deveRejeitarPedidoQuandoProdutoEstaSemEstoque() {
        when(pedidoRepository.clienteExiste(1))
                .thenReturn(true);

        when(pedidoRepository.buscarProdutoParaPedido(2))
                .thenReturn(Map.of(
                        "estoque_disponivel", 0,
                        "preco", 4.50
                ));

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.criarPedido(1, 2)
        );

        verify(pedidoRepository, never())
                .descontarEstoque(anyInt());
        verify(pedidoRepository, never())
                .inserirPedido(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void naoDeveCancelarPedidoQueJaFoiCancelado() {
        when(pedidoRepository.buscarParaCancelamento(10))
                .thenReturn(Map.of(
                        "id", 10,
                        "produto_id", 2,
                        "situacao", "CANCELADO"
                ));

        assertThrows(
                IllegalArgumentException.class,
                () -> pedidoService.cancelarPedido(10)
        );

        verify(pedidoRepository, never())
                .cancelarPedido(anyInt());
        verify(pedidoRepository, never())
                .devolverEstoque(anyInt());
    }
}
