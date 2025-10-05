# API - Gerenciador de Tarefas

API RESTful desenvolvida como parte do trabalho da disciplina de Desenvolvimento Web para gerenciar uma lista de tarefas (To-Do List).

## Tecnologias Utilizadas

* Java 17
* Spring Boot 3+
* Spring Data JPA
* Maven
* PostgreSQL

## Endpoints da API

A URL base para acessar a API é `http://localhost:8081/tarefas-api`.

| Método HTTP | URL                      | Ação                                     | Exemplo de Corpo (Body) para Envio                               |
|-------------|--------------------------|------------------------------------------|-----------------------------------------------------------------|
| `POST`      | `/`                      | Criar uma nova tarefa.                   | `{"descricao": "Estudar para a prova", "prioridade": 1}`        |
| `GET`       | `/`                      | Listar todas as tarefas.                 | N/A                                                             |
| `GET`       | `/?descricao={texto}`    | Listar tarefas filtrando por descrição.  | N/A                                                             |
| `GET`       | `/{id}`                  | Obter uma tarefa específica pelo seu ID.   | N/A                                                             |
| `PUT`       | `/{id}`                  | Atualizar uma tarefa por completo.       | `{"descricao": "Tarefa atualizada", "concluida": true, "prioridade": 2}` |
| `DELETE`    | `/{id}`                  | Deletar uma tarefa pelo seu ID.          | N/A                                                             |
| `GET`       | `/pendentes`             | Listar apenas as tarefas não concluídas.   | N/A                                                             |
| `PATCH`     | `/{id}/concluir`         | Marcar uma tarefa como concluída.        | N/A                                                             |

---

## Como Executar o Projeto

Para executar este projeto localmente, siga os passos abaixo:

1.  **Pré-requisitos:**
    * Java 17
    * Maven
    * PostgreSQL

2.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/SeuUsuario/api-gerenciador-tarefas.git](https://github.com/SeuUsuario/api-gerenciador-tarefas.git)
    ```

3.  **Configure o Banco de Dados:**
    * Certifique-se de que seu servidor PostgreSQL está rodando.
    * Crie um banco de dados com o nome `tarefasdb`.

4.  **Configure as Variáveis de Ambiente:**
    * Navegue até o arquivo `src/main/resources/application.properties`.
    * Altere as propriedades `spring.datasource.username` e `spring.datasource.password` com os seus dados de acesso ao banco.
    ```properties
    spring.datasource.username=seu_usuario_postgres
    spring.datasource.password=sua_senha_aqui
    ```

5.  **Execute a Aplicação:**
    * Abra o projeto na sua IDE de preferência.
    * Execute a classe principal `TarefasApiApplication.java`.
    * A API estará disponível para testes em `http://localhost:8081/tarefas-api`.
