
package br.com.cantina.domain;

public class Cliente {

    private long id;
    private String nome;
    private String email;

    public Cliente(long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
