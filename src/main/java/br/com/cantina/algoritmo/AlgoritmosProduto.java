package br.com.cantina.algoritmo;

import br.com.cantina.domain.Produto;
import java.util.List;

public class AlgoritmosProduto {

    public Produto buscaLinear(List<Produto> produtos, long id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }

        return null;
    }

    public Produto buscaBinaria(List<Produto> produtos, long id) {
        int inicio = 0;
        int fim = produtos.size() - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;

            Produto produto = produtos.get(meio);

            if (produto.getId() == id) {
                return produto;
            }

            if (produto.getId() < id) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return null;
    }

    
    public List<Produto> bubbleSort(List<Produto> produtos) {
        List<Produto> ordenados = new java.util.ArrayList<>(produtos);

        for (int i = 0; i < ordenados.size() - 1; i++) {
            for (int j = 0; j < ordenados.size() - 1 - i; j++) {

                if (ordenados.get(j).getId()
                        > ordenados.get(j + 1).getId()) {

                    Produto temporario = ordenados.get(j);

                    ordenados.set(j, ordenados.get(j + 1));
                    ordenados.set(j + 1, temporario);
                }
            }
        }

        return ordenados;
    }

}