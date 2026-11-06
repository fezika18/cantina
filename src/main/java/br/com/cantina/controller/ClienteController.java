package br.com.cantina.controller;

import br.com.cantina.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(
            @PathVariable int id) {

        Map<String, Object> cliente =
                clienteService.buscarPorId(id);

        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }
}