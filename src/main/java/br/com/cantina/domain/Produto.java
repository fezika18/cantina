package br.com.cantina.domain;

import java.math.BigDecimal;

public class Produto {

    private long id;
    private String nome;
    private BigDecimal preco;
    private int estoqueDisponivel;

    public Produto(
            long id,
            String nome,
            BigDecimal preco,
            int estoqueDisponivel) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoqueDisponivel = estoqueDisponivel;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getEstoqueDisponivel() {
        return estoqueDisponivel;
    }
}