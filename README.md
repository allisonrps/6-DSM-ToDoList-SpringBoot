# 🚀 6-DSM-ToDoList-SpringBoot & React

## 👨‍💻 Desenvolvedores

**Allison Rodrigues de Paula e Silva**  
**Paula Cristina Abib Teixeira**

---

Um projeto **Full-Stack de Lista de Tarefas (To-Do List)** desenvolvido para fins acadêmicos e demonstrativos.  
A aplicação integra um **backend robusto em Java (Spring Boot)** com um **frontend moderno em React**, permitindo criar, gerenciar e acompanhar tarefas de forma prática.  

---

## ✨ Visão Geral

Este projeto mostra como construir uma aplicação moderna, onde:  
- O **Backend** é responsável pela lógica de negócio e persistência no banco de dados.  
- O **Frontend** consome a API e fornece uma **interface interativa e responsiva** para o usuário.  

---

## ⚙️ Tecnologias Utilizadas

| Componente | Tecnologia | Detalhes |
|------------|------------|-----------|
| **Backend (API)** | Java 17+, Spring Boot 3.x, Maven | REST API |
| **Persistência** | Spring Data JPA + Hibernate | ORM |
| **Banco de Dados** | PostgreSQL (porta 5432) | Relacional |
| **Frontend (SPA)** | React.js |
| **Comunicação** | Axios | Cliente HTTP |

---

## 🎯 Funcionalidades

- ✅ **Criar** tarefas com *Nome*, *Descrição* e *Observações*  
- 📋 **Listar** todas as tarefas com seus detalhes  
- ✏️ **Editar/Atualizar** informações da tarefa  
- 🔄 **Alterar Status** (entre `PENDENTE` e `CONCLUIDA`)  
- 🗑️ **Excluir** tarefas  

---

## 📂 Estrutura de Diretórios

```
.
├── backend/   # BACKEND (Java + Spring Boot)
└── frontend/  # FRONTEND (React + npm)     
```

---

## ⚙️ Configuração e Execução

### 🔑 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:  
- **Java JDK 17+**  
- **PostgreSQL**
- **React + npm**  

---

### 🛠️ Passo 1: Configurar o Banco de Dados

No PostgreSQL, execute o seguinte script para criar o banco **`to_do`** e a tabela **`tarefa`**:  

```sql
-- Criar database
CREATE DATABASE to_do;

-- Conectar ao banco
\c to_do;

-- Criar tabela
CREATE TABLE tarefa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    observacoes TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

Depois, ajuste as credenciais no arquivo  
**`/src/main/resources/application.properties`**:  

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/to_do
spring.datasource.username=postgres
spring.datasource.password=suasenhapostgres
spring.jpa.hibernate.ddl-auto=update
```

---

### 🚀 Passo 2: Rodar o Backend (Spring Boot)

No terminal, vá até a pasta do backend:  
```bash
cd backend
```

Inicie a aplicação:  
```bash
mvn clean install 
mvn spring-boot:run 
```

📡 A API estará disponível em: **http://localhost:8080**

---

### 💻 Passo 3: Rodar o Frontend (React)

Abra outro terminal e vá até a pasta do frontend:  
```bash
cd frontend
```

Instale as dependências:  
```bash
npm install
```

Inicie o servidor React:  
```bash
npm start
```

🌐 O frontend abrirá automaticamente em **http://localhost:3000**

---

## ✅ Executando Testes (Backend)

Para rodar os testes unitários do Spring Boot:  
```bash
cd backend
mvn test
```
