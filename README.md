# Sistema de Pedidos de Cantina
Sistema de gerenciamento de produtos, clientes e pedidos de uma cantina, desenvolvido em Java com Spring Boot e PostgreSQL.

## 5. Endpoints da API

### Clientes

| Método | Endpoint | Operação |
|---|---|---|
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/{id}` | Consultar cliente por ID |

### Produtos

| Método | Endpoint | Operação |
|---|---|---|
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Consultar produto por ID |
| PUT | `/produtos/{id}` | Atualizar nome e preço |
| DELETE | `/produtos/{id}` | Excluir produto quando permitido |

## 6. Módulo de algoritmos

O módulo `AlgoritmosProduto` implementa três algoritmos independentes das rotas REST.

- **Busca linear:** percorre a lista até encontrar o produto pelo ID ou concluir que ele não existe.
- **Busca binária:** reduz pela metade o intervalo pesquisado a cada iteração. A lista deve estar previamente ordenada pelo ID.
- **Bubble Sort:** compara elementos vizinhos e troca suas posições quando necessário. A implementação retorna uma nova lista ordenada por ID, sem alterar a lista original.

As complexidades representam o tempo de execução esperado em função da quantidade de elementos.
