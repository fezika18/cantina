package br.com.cantina.controller;

import br.com.cantina.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(
            @PathVariable int id) {

        Map<String, Object> produto =
                produtoService.buscarPorId(id);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastrar(
            @RequestBody Map<String, Object> dados) {

        String nome = (String) dados.get("nome");
        double preco =
                ((Number) dados.get("preco")).doubleValue();
        int estoque =
                ((Number) dados.get("estoque_disponivel")).intValue();

        produtoService.cadastrar(nome, preco, estoque);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Produto cadastrado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> atualizar(
            @PathVariable int id,
            @RequestBody Map<String, Object> dados) {

        String nome = (String) dados.get("nome");
        double preco = ((Number) dados.get("preco")).doubleValue();

        int linhasAlteradas = produtoService.atualizar(id, nome, preco);

        if (linhasAlteradas == 0) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> resposta = Map.of(
            "mensagem", "Produto atualizado com sucesso!"
        );

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> excluir(
            @PathVariable int id) {

        produtoService.excluir(id);

        Map<String, String> resposta = Map.of(
            "mensagem", "Produto excluído com sucesso!"
        );

        return ResponseEntity.ok(resposta);
    }
}