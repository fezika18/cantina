# Sistema de Pedidos de Cantina

## 1. Descrição do projeto

Sistema de gerenciamento de produtos, clientes e pedidos de uma cantina, desenvolvido em Java com Spring Boot e PostgreSQL.

A aplicação disponibiliza uma API REST para consultar clientes, cadastrar e administrar produtos, registrar pedidos e cancelar pedidos. O sistema aplica regras de negócio para controlar o estoque e preservar o preço registrado no momento da criação de cada pedido.

## 2. Tecnologias utilizadas

- Java 17
- Spring Boot
- Maven
- PostgreSQL
- JDBC / JdbcTemplate
- JUnit 5
- Mockito
- Postman
- Git e GitHub

## 3. Arquitetura

O projeto utiliza uma arquitetura em camadas, separando as responsabilidades:

- **Controller:** recebe as requisições HTTP e encaminha as operações para os serviços.
- **Service:** implementa as regras de negócio e valida as operações.
- **Repository:** executa as consultas e alterações no PostgreSQL utilizando JDBC.
- **Domain:** representa as entidades do domínio, como Produto, Cliente e Pedido.
- **Algoritmo:** contém os algoritmos de busca e ordenação de produtos, separados dos endpoints da API.

Fluxo principal:

`Postman → Controller → Service → Repository → PostgreSQL`

## 4. Regras de negócio

- Os clientes são somente consultados pela API.
- O cliente e o produto devem existir para que um pedido seja registrado.
- O produto precisa ter estoque disponível.
- Cada pedido registra um único produto e uma unidade.
- O preço é copiado para o pedido no momento da criação, preservando o histórico.
- A criação do pedido e a redução do estoque devem ocorrer na mesma transação.
- O cancelamento e a devolução da unidade ao estoque também devem ocorrer na mesma transação.
- Um pedido cancelado não pode ser cancelado novamente.
- O estoque não pode ficar negativo.
- Produtos com histórico de pedidos não podem ser excluídos.
- A atualização cadastral do produto permite alterar o nome e o preço, sem movimentar o estoque.

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

### Pedidos

| Método | Endpoint | Operação |
|---|---|---|
| POST | `/pedidos` | Criar pedido e reduzir o estoque |
| GET | `/pedidos` | Listar o histórico de pedidos |
| PATCH | `/pedidos/{id}/cancelamento` | Cancelar pedido e devolver uma unidade ao estoque |

O projeto também implementa `GET /pedidos/{id}` para consultar um pedido pelo identificador.

## 6. Módulo de algoritmos

O módulo `AlgoritmosProduto` implementa três algoritmos independentes das rotas REST.

- **Busca linear:** percorre a lista até encontrar o produto pelo ID ou concluir que ele não existe.
- **Busca binária:** reduz pela metade o intervalo pesquisado a cada iteração. A lista deve estar previamente ordenada pelo ID.
- **Bubble Sort:** compara elementos vizinhos e troca suas posições quando necessário. A implementação retorna uma nova lista ordenada por ID, sem alterar a lista original.

As complexidades representam o tempo de execução esperado em função da quantidade de elementos.

## 7. Execução do projeto

### Pré-requisitos

- JDK 17 ou superior.
- Acesso ao banco PostgreSQL utilizado pelo projeto.
- Maven Wrapper incluído no repositório.

### Configuração do banco

O arquivo `application.properties` utiliza as seguintes variáveis:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Configure os valores no ambiente local ou no arquivo `.env` utilizado pela aplicação. Não versionar credenciais reais nem o arquivo `.env`.

### Compilação

Na pasta raiz do projeto, execute:

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

Por padrão, a API utiliza a porta `8080`, salvo configuração diferente.

## 8. Testes automatizados

Os testes são executados pelo Maven com JUnit 5.

A execução registrada apresentou o seguinte resultado:

| Resultado | Quantidade |
|---|---:|
| Testes executados | 10 |
| Falhas | 0 |
| Erros | 0 |
| Ignorados | 0 |

Os testes abrangem:

- Busca linear de produto existente e inexistente.
- Busca binária em lista ordenada.
- Ordenação crescente e preservação da lista original.
- Rejeição de cadastro com preço igual a zero.
- Rejeição de cadastro com estoque negativo.
- Rejeição de pedido com cliente inexistente.
- Rejeição de pedido de produto sem estoque.
- Rejeição de cancelamento repetido.
- Inicialização do contexto da aplicação Spring Boot.

**Evidência:** `docs/evidencias/Junit/resultado-maven.png`

## 9. Refatoração realizada

**Problema identificado:** as validações de nome e preço estavam duplicadas nos métodos de cadastro e atualização de produtos.

**Alteração:** as validações compartilhadas foram extraídas para o método privado `validarDadosProduto()`, utilizado pelas operações de cadastro e atualização. A validação de estoque permaneceu no cadastro por ser específica dessa operação.

**Validação:** os testes automatizados existentes foram executados novamente pelo Maven, incluindo cenários de preço inválido e estoque negativo.

**Benefício:** redução da duplicação de código e centralização das regras de validação compartilhadas.

## 10. Evidências da API

As capturas das requisições e respostas do Postman estão organizadas em `docs/evidencias/Postman/`.

- Consulta de clientes.
- Cadastro, consulta e atualização de produtos.
- Exclusão de produto sem histórico.
- Criação e consulta de pedidos.
- Cancelamento de pedido.
- Tentativa de cancelamento repetido.
- Tentativa de pedido com produto sem estoque.
- Tentativa de exclusão de produto com pedido aberto.
- Tentativa de pedido com cliente inexistente.

## 11. Diagrama UML

O diagrama de classes (UML) estão localizados no diretório abaixo:

- `docs/diagrama-classes.drawio` — diagrama editável.
- `docs/diagrama-classes.png` — exportação para visualização.

O diagrama deve ser mantido coerente com o código Java entregue.

## 12. Uso de inteligência artificial

Ferramentas de inteligência artificial foram utilizadas como apoio à compreensão de conceitos, implementação, revisão e documentação do projeto. O código foi compilado e testado, e os integrantes devem compreender as decisões técnicas presentes na entrega.

## 13. Integrantes

- Felipe Rogério — RA 5425921
- Julia Diniz — RA 5371333
- Vithor Andrade — RA 5373727
- Eduardo Mateus — RA 5456681
- Thomas Medeiros — RA 5417813

## 14. Repositório e apresentação

- [Repositório GitHub do projeto](https://github.com/fezika18/cantina)
- [Diretório da apresentação](`docs/`)