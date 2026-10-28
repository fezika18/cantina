# Sistema de Pedidos de Cantina

## 1. Descrição do projeto

Sistema de gerenciamento de produtos, clientes e pedidos de uma cantina, desenvolvido em Java com Spring Boot e PostgreSQL.

A aplicação disponibiliza endpoints REST para consultar clientes, cadastrar e administrar produtos, registrar pedidos e cancelar pedidos. As operações de pedido respeitam as regras de estoque e o registro histórico do preço do produto.

## 2. Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- JDBC / JdbcTemplate
- JUnit 5
- Postman
- Git e GitHub

## 3. Arquitetura

O projeto está organizado em camadas:

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints.
- **Service:** concentra as regras de negócio e validações.
- **Repository:** executa as operações no banco de dados usando JDBC.
- **Domain:** contém a representação dos objetos do domínio.
- **Algoritmo:** implementa os algoritmos de busca e ordenação de produtos.

O fluxo principal é:

`Postman → Controller → Service → Repository → PostgreSQL`

## 4. Regras de negócio

- Os clientes são consultados, mas não podem ser cadastrados ou alterados pela API.
- Para registrar um pedido, o cliente e o produto devem existir.
- O produto precisa ter estoque disponível.
- Cada pedido registra um produto e uma unidade.
- O preço é registrado no momento da criação do pedido.
- A criação do pedido e a redução do estoque devem ocorrer na mesma transação.
- O cancelamento só pode ocorrer uma vez para cada pedido ativo.
- Ao cancelar um pedido, a unidade correspondente retorna ao estoque.
- As alterações de estoque relacionadas aos pedidos devem respeitar o histórico de pedidos.
- A exclusão de produtos com histórico de pedidos deve ser impedida.

## 5. Endpoints

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
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Excluir produto, respeitando as regras de histórico |

### Pedidos

| Método | Endpoint | Operação |
|---|---|---|
| POST | `/pedidos` | Criar pedido |
| GET | `/pedidos` | Listar pedidos |
| GET | `/pedidos/{id}` | Consultar pedido por ID |
| PATCH | `/pedidos/{id}/cancelamento` | Cancelar pedido |

## 6. Módulo de algoritmos

O módulo `AlgoritmosProduto` implementa:

- **Busca linear:** percorre os produtos até encontrar o ID desejado. Complexidade de tempo: `O(n)`.
- **Busca binária:** divide sucessivamente o intervalo de busca pela metade. Complexidade de tempo: `O(log n)`. Requer que a lista esteja previamente ordenada pelo ID.
- **Bubble Sort:** compara elementos vizinhos e troca suas posições quando necessário. Complexidade de tempo média e de pior caso: `O(n²)`.

O Bubble Sort retorna uma nova lista ordenada por ID, sem alterar a lista recebida originalmente.

## 7. Execução do projeto

### Pré-requisitos

- Java 21 instalado ou configurado no ambiente.
- Banco PostgreSQL acessível.
- Maven Wrapper incluído no projeto.

### Configuração do banco

Configure as variáveis de ambiente utilizadas em `application.properties`:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

No ambiente local, elas podem ser configuradas no arquivo `.env`, que não deve ser enviado ao repositório com credenciais reais.

### Compilação

No terminal, na pasta raiz do projeto:

```powershell
.\mvnw.cmd compile
```

### Testes automatizados

```powershell
.\mvnw.cmd test
```

### Inicialização da aplicação

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação utiliza a porta `8080`, salvo configuração diferente.

## 8. Testes

Os testes automatizados do módulo de algoritmos utilizam JUnit 5 e cobrem:

- Busca linear de um produto existente.
- Busca linear de um produto inexistente.
- Busca binária em uma lista ordenada.
- Ordenação crescente por ID com Bubble Sort.
- Preservação da lista original durante a ordenação.

**Comando de execução:** `.\mvnw.cmd test`

**Evidência:** adicionar uma captura do terminal mostrando o resultado da execução e a quantidade de testes aprovados.

## 9. Decisões de implementação

- Utilização de JDBC/JdbcTemplate para persistência em PostgreSQL.
- Separação das responsabilidades entre Controller, Service e Repository.
- Uso de transações nas operações de criação e cancelamento de pedidos.
- Registro do preço histórico no momento da criação do pedido.
- Implementação dos algoritmos em um módulo independente dos endpoints REST.
- Uso de uma coleção de produtos em memória nos testes de algoritmos.

## 10. Uso de inteligência artificial

Ferramentas de inteligência artificial foram utilizadas como apoio ao desenvolvimento, à compreensão de conceitos, à implementação e à revisão de código. As sugestões foram aplicadas e verificadas no projeto pelos integrantes, com execução de compilação e testes.

## 11. Integrantes

- Preencher com os nomes completos dos integrantes do grupo.

## 12. Evidências e materiais da entrega

Adicionar os links ou caminhos dos materiais finais:

- Repositório GitHub.
- Coleção oficial do Postman e evidências dos endpoints.
- Diagrama UML atualizado conforme o código.
- Capturas dos testes automatizados.
- Evidências do histórico de commits e das refatorações.
- Link do vídeo de apresentação, respeitando o limite de duração estabelecido pelo professor.