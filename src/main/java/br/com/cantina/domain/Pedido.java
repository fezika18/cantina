
package br.com.cantina.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {

    private long id;
    private Cliente cliente;
    private Produto produto;
    private BigDecimal precoRegistrado;
    private LocalDateTime dataRegistro;
    private String situacao;

    public Pedido(
            long id,
            Cliente cliente,
            Produto produto,
            BigDecimal precoRegistrado,
            LocalDateTime dataRegistro,
            String situacao) {

        this.id = id;
        this.cliente = cliente;
        this.produto = produto;
        this.precoRegistrado = precoRegistrado;
        this.dataRegistro = dataRegistro;
        this.situacao = situacao;
    }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getPrecoRegistrado() {
        return precoRegistrado;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public String getSituacao() {
        return situacao;
    }
}
