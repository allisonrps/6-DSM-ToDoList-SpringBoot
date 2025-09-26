# 🚀 6-DSM-ToDoList-SpringBoot & React

Um projeto de **Lista de Tarefas (To-Do List)** Full-Stack desenvolvido para fins acadêmicos/demonstrativos, demonstrando a integração entre um backend Java e um frontend moderno em JavaScript.

---

## ✨ Visão Geral do Projeto

Este projeto demonstra a arquitetura de uma aplicação moderna, onde o **Backend** gerencia a lógica de negócio e o banco de dados, e o **Frontend** consome a API para fornecer uma interface de usuário rica.

### ⚙️ Tecnologias Utilizadas

| Componente | Tecnologia | Detalhes |
| :--- | :--- | :--- |
| **Backend (API)** | **Spring Boot 3.x** | Java, Maven, REST API. |
| **Persistência** | **Spring Data JPA** | Hibernate para mapeamento ORM. |
| **Banco de Dados** | **PostgreSQL** | Banco de dados relacional (porta 5432). |
| **Frontend (SPA)** | **React.js** | Interface do usuário e lógica de estado. |
| **Comunicação** | **Axios** | Cliente HTTP para requisições à API. |

### 🎯 Funcionalidades da Aplicação

A aplicação permite ao usuário:

* **Criar** uma nova tarefa (com Nome, Descrição e Observações).
* **Listar** todas as tarefas com detalhes completos.
* **Atualizar/Editar** o nome, descrição, observações e status de uma tarefa.
* **Mudar o Status** (de `PENDENTE` para `CONCLUIDA` e vice-versa).
* **Deletar** tarefas.

---

## 🏗️ Estrutura de Diretórios

O projeto está organizado em duas pastas principais, refletindo a arquitetura Full-Stack:

.
├── 6-DSM-ToDoList-SpringBoot/  <-- BACKEND (Java/Maven)
└── FRONT/
└── todo-frontend/          <-- FRONTEND (React/npm)


---

## ⚙️ Configuração e Execução

Siga os passos abaixo para colocar o Backend e o Frontend no ar.

### Pré-requisitos

Certifique-se de ter instalado:

1.  **Java JDK 17** ou superior.
2.  **PostgreSQL** (Servidor rodando na porta padrão `5432`).
3.  **Node.js e npm** (para o Frontend).

### Passo 1: Configurar o Banco de Dados

1.  Crie um banco de dados vazio no PostgreSQL chamado **`to_do`**.
2.  Verifique (e ajuste, se necessário) as credenciais no arquivo **`6-DSM-ToDoList-SpringBoot/src/main/resources/application.properties`**:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/to_do
spring.datasource.username=postgres
spring.datasource.password=123456
(O Hibernate criará automaticamente a tabela tarefa na primeira execução.)

Passo 2: Iniciar o Backend (Spring Boot)
Navegue até o diretório raiz do Backend:

Bash

cd 6-DSM-ToDoList-SpringBoot
Inicie a aplicação utilizando o wrapper Maven:

Bash

./mvnw spring-boot:run
(No Windows, use .\mvnw spring-boot:run)

A API estará disponível na porta 8080.

Passo 3: Iniciar o Frontend (React)
Abra um novo terminal e navegue até o diretório do Frontend:

Bash

cd FRONT/todo-frontend
Instale as dependências (se for a primeira vez):

Bash

npm install
Inicie a aplicação React:

Bash

npm start
O Frontend será aberto automaticamente em seu navegador na porta 3000 (http://localhost:3000).

✅ Executando Testes (Backend)
Para garantir que a lógica de negócio e os endpoints da API estejam funcionando corretamente, execute os testes unitários do Spring Boot:

Bash

cd 6-DSM-ToDoList-SpringBoot
./mvnw test