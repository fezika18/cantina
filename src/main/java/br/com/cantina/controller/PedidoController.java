package br.com.cantina.controller;

import br.com.cantina.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> criarPedido(
            @RequestBody Map<String, Object> dados) {

        int clienteId =
                ((Number) dados.get("cliente_id")).intValue();

        int produtoId =
                ((Number) dados.get("produto_id")).intValue();

        pedidoService.criarPedido(clienteId, produtoId);

        Map<String, String> resposta = Map.of(
            "mensagem", "Pedido criado com sucesso!"
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(
            @PathVariable int id) {

        Map<String, Object> pedido =
                pedidoService.buscarPorId(id);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<Map<String, String>> cancelarPedido(
            @PathVariable int id) {

        pedidoService.cancelarPedido(id);

        Map<String, String> resposta = Map.of(
            "mensagem", "Pedido cancelado com sucesso!"
        );

        return ResponseEntity.ok(resposta);
    }
}