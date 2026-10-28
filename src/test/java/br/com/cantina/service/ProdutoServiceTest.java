
package br.com.cantina.service;

import br.com.cantina.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRejeitarPrecoIgualAZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> produtoService.cadastrar("Cafe", 0.0, 10)
        );

        verify(produtoRepository, never())
            .cadastrar(anyString(), anyDouble(), anyInt());
    }

    @Test
    void deveRejeitarEstoqueNegativo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> produtoService.cadastrar("Cafe", 4.50, -1)
        );

        verify(produtoRepository, never())
            .cadastrar(anyString(), anyDouble(), anyInt());
    }
}
