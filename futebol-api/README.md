#Futebol API — Gerenciamento de Mensalistas
API REST em Java com Spring Boot para gerenciar pagamentos de mensalistas em jogos de futebol.
---

##Estrutura do Projeto (MVC)

futebol-api/
├── pom.xml
└── src/main/java/com/futebol/
    ├── FutebolApiApplication.java        ← Ponto de entrada
    ├── model/
    │   ├── Jogador.java                  ← Entidade JPA
    │   └── Pagamento.java                ← Entidade JPA
    ├── repository/
    │   ├── JogadorRepository.java        ← Spring Data JPA
    │   └── PagamentoRepository.java
    ├── service/
    │   ├── JogadorService.java           ← Regras de negócio
    │   └── PagamentoService.java
    └── controller/
        ├── JogadorController.java        ← Endpoints REST
        ├── PagamentoController.java
        └── GlobalExceptionHandler.java   ← Tratamento de erros
---

## Como executar

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Comandos
```bash
# Na pasta raiz do projeto:
mvn spring-boot:run

A aplicação sobe em `http://localhost:8080`.

> Console H2 (banco em memória): `http://localhost:8080/h2-console`  
> JDBC URL: `jdbc:h2:mem:futebolddb` | User: `sa` | Password: *(vazio)*
---

## Endpoints da API

### Jogadores — `/jogadores`

| Método | URL                     | Descrição                            |
|--------|-------------------------|--------------------------------------|
| GET    | `/jogadores`            | Lista todos os jogadores             |
| GET    | `/jogadores?nome=Carlos`| Filtra jogadores por nome            |
| GET    | `/jogadores/{id}`       | Busca jogador por código             |
| POST   | `/jogadores`            | Cadastra novo jogador                |
| PUT    | `/jogadores/{id}`       | Atualiza jogador existente           |
| DELETE | `/jogadores/{id}`       | Remove jogador                       |

**Corpo JSON (POST/PUT):**
```json
{
  "nome": "Carlos Silva",
  "email": "carlos@email.com",
  "datanasc": "1995-03-15"
}
```

---

### Pagamentos — `/pagamentos`

| Método | URL                                        | Descrição                                  |
|--------|--------------------------------------------|--------------------------------------------|
| GET    | `/pagamentos`                              | Lista todos os pagamentos                  |
| GET    | `/pagamentos/{id}`                         | Busca pagamento por código                 |
| GET    | `/pagamentos/jogador/{codJogador}`         | Pagamentos de um jogador                   |
| GET    | `/pagamentos/jogador/{id}?ano=2025`        | Pagamentos do jogador em um ano            |
| GET    | `/pagamentos/jogador/{id}?ano=2025&mes=3`  | Pagamento do jogador em mês/ano específico |
| POST   | `/pagamentos/jogador/{codJogador}`         | Registra pagamento para o jogador          |
| PUT    | `/pagamentos/{id}`                         | Atualiza pagamento existente               |
| DELETE | `/pagamentos/{id}`                         | Remove pagamento                           |

**Corpo JSON (POST/PUT):**
```json
{
  "ano": 2025,
  "mes": 3,
  "valor": 150.00
}
```

---

## Modelo de Dados

```
jogador                          pagamento
─────────────────────            ──────────────────────────
cod_jogador  INTEGER [PK]  ───╗  cod_pagamento INTEGER [PK]
nome         VARCHAR(60)       ╚═ ano           SMALLINT
email        VARCHAR(60)       ╠═ mes           TINYINT
datanasc     DATE              ║  valor         NUMERIC
                               ╚  cod_jogador   INTEGER [FK]
```

Relacionamento: **Um jogador → Muitos pagamentos** (`@OneToMany` / `@ManyToOne`)

---

## Anotações JPA utilizadas

| Anotação         | Onde              | Finalidade                                     |
|------------------|-------------------|------------------------------------------------|
| `@Entity`        | Jogador, Pagamento| Marca a classe como entidade JPA               |
| `@Table`         | Jogador, Pagamento| Define o nome da tabela no banco               |
| `@Id`            | Ambas             | Define a chave primária                        |
| `@GeneratedValue`| Ambas             | Auto-incremento da PK                          |
| `@OneToMany`     | Jogador           | Um jogador possui muitos pagamentos            |
| `@ManyToOne`     | Pagamento         | Muitos pagamentos pertencem a um jogador       |
| `@JoinColumn`    | Pagamento         | Define a FK `cod_jogador` na tabela pagamento  |

---

## Uso com MySQL (produção)

Descomente no `application.properties` e ajuste usuário/senha:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/futebolddb?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

Adicione também a dependência MySQL no `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```
