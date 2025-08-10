# Pedidos API - Desafio Técnico BTG

##  Visão Geral
API para criação e processamento de pedidos utilizando **Spring Boot (Kotlin)** e **RabbitMQ** para mensageria.  
Após a criação, o pedido é enviado para uma fila e processado assincronamente.

---

##  Tecnologias
- **Kotlin + Spring Boot 3**
- **RabbitMQ** (mensageria)
- **Jakarta Validation** (validações)
- **JUnit + MockMvc + Mockito** (testes)
- **Docker/Docker Compose** (ambiente local)

---

##  Arquitetura

Controller → Service → Repository (in-memory)
↓

Producer → RabbitMQ → Consumer → Atualiza status


- **Controller**: recebe requisições HTTP, valida entrada e retorna respostas padronizadas.
- **Service**: lógica de negócio (criar pedido, buscar, processar).
- **Repository**: armazenamento em memória (mock).
- **Producer**: envia ID do pedido para fila no RabbitMQ.
- **Consumer**: consome ID da fila e marca o pedido como `PROCESSADO`.

---

##  Executando Localmente

### 1. Subir RabbitMQ
```bash
docker-compose up -d
```

### 2. Rodar projeto
```
Rodar projeto, pedidosApplication.kt
```
### Endpoints

#### POST /pedidos

Content-Type: application/json

```
{
"clienteId": "João da Silva",
"itens": ["item1", "item2"]
}
```

Response:
```
{
"id": "UUID",
"clienteId": "João da Silva",
"itens": ["item1", "item2"],
"status": "PENDENTE"
}
```
#### GET /pedidos/{id}

Response:

```
{
"id": "UUID",
"clienteId": "João da Silva",
"itens": ["item1", "item2"],
"status": "PENDENTE"
}
```





