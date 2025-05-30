
# 📋 Sobre o Projeto

A **ComunicaPlus API** é uma aplicação backend desenvolvida com **Spring Boot 3**, projetada para facilitar a comunicação entre dispositivos através do envio, recebimento e gerenciamento de mensagens. Ideal para sistemas de IoT ou ambientes distribuídos onde múltiplos dispositivos se comunicam entre si.

As mensagens possuem informações como:

- 📬 Conteúdo
- 📱 Dispositivo Remetente
- 📥 Dispositivo Destinatário
- ⏰ Timestamp
- 🚚 Status de entrega e encaminhamento

Com suporte a **filtros dinâmicos** via Specification e **cache** de resultados para otimizar performance.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security (JWT)
- Lombok
- Spring Cache
- H2 Database (em memória)
- Specification API (para filtros dinâmicos)
- Swagger / OpenAPI 3
- Spring DevTools (hot reload)

---

## ⚙️ Como Rodar o Projeto

### ✅ Pré-requisitos:

- Java 17+
- Maven 3.8+

### 🚀 Executar localmente:

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/comunicaplus-api-main.git

# Acesse o diretório
cd comunicaplus-api-main

# Execute o projeto
./mvnw spring-boot:run
```

---

## 🔗 Principais Endpoints

| Método | Endpoint                | Descrição                          |
|--------|-------------------------|------------------------------------|
| GET    | `/api/messages`         | Listar mensagens (com filtros)     |
| POST   | `/api/messages`         | Criar uma nova mensagem            |
| GET    | `/api/messages/{id}`    | Buscar mensagem por ID             |
| PUT    | `/api/messages/{id}`    | Atualizar mensagem existente       |
| DELETE | `/api/messages/{id}`    | Deletar mensagem                   |

---

## 🔍 Exemplos de Uso no Postman

### 🔐 1. Autenticar e obter Token JWT

```http
POST http://localhost:8080/auth/login
```

**Body (JSON):**
```json
{
  "email": "admin@example.com",
  "password": "admin123"
}
```

**Resposta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use o token nos headers:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

---

### 🚀 2. Listar todas as mensagens

```http
GET http://localhost:8080/api/messages
```

### 🚀 3. Filtrar mensagens

Buscar por conteúdo:
```http
GET /api/messages?content=socorro
```

Buscar por ID do dispositivo remetente:
```http
GET /api/messages?deviceId=1
```

Combinar filtros:
```http
GET /api/messages?content=hello&deviceId=2
```

---

### 🚀 4. Criar uma nova mensagem

```http
POST http://localhost:8080/api/messages
```

**Body (JSON):**
```json
{
  "content": "Olá, mundo!",
  "timestamp": "2025-05-29T10:00:00",
  "sender": { "id": 1 },
  "recipient": { "id": 2 },
  "delivered": false,
  "forwarded": false
}
```

---

### 🚀 5. Atualizar uma mensagem

```http
PUT /api/messages/1
```

**Body (JSON):**
```json
{
  "content": "Mensagem atualizada",
  "timestamp": "2025-05-29T10:30:00",
  "sender": { "id": 1 },
  "recipient": { "id": 2 },
  "delivered": true,
  "forwarded": false
}
```

---

### 🚀 6. Deletar uma mensagem

```http
DELETE /api/messages/1
```

---

## 📚 Documentação Swagger

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

✅ Interface interativa para testar todos os endpoints!

---

## 🛃 Banco de Dados

- Banco atual: **H2 Database (memória)**
- JDBC URL: `jdbc:h2:mem:comunicaplus`
- O projeto pode ser facilmente adaptado para Oracle, PostgreSQL ou MySQL via `application.properties`.

---

## 👥 Sobre o Grupo

| Nome                        | RM      |
|-----------------------------|---------|
| Leonardo da Silva Pereira  | 557598  |
| Bruno da Silva Souza       | 94346   |
| Julio Samuel de Oliveira   | 557453  |
