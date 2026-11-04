# API Clínica Veterinária

API REST para cadastro de tutores, pets e agendamento de consultas.

## Tecnologias

- Java 17
- Spring Boot 3.5 (Web, Validation, Data JPA)
- PostgreSQL 16 (Docker)
- Lombok
- Springdoc OpenAPI (Swagger)
- Maven
- JUnit 5 + Mockito

## Como executar

1. Crie o arquivo `.env` na raiz a partir do modelo `.env.example` e preencha a senha.
2. Suba o banco:

```bash
docker compose up -d
```

3. Rode a classe `ClinicaApplication` pela IDE (ou `mvn spring-boot:run`).

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Banco: `localhost:5433`

Testes:

```bash
mvn test
```

## Modelo

```
Tutor 1 ──── N Pet 1 ──── N Consulta
```

- **Tutor**: nome, email (único), telefone
- **Pet**: nome, especie (`CACHORRO`, `GATO`, `PASSARO`, `OUTRO`), raca, dataNascimento, tutor
- **Consulta**: pet, dataHora, motivo, valor, status (`AGENDADA`, `REALIZADA`, `CANCELADA`)

## Estrutura

```
controller  -> recebe a requisição HTTP e devolve ResponseEntity
service     -> regras de negócio
repository  -> acesso aos dados (Spring Data JPA)
model       -> classes de domínio e enums
dto         -> o que entra (Request) e o que sai (Response) da API
mapper      -> conversão Entity <-> DTO
exception   -> exceções da aplicação e GlobalExceptionHandler
```

## Endpoints

### Tutores

| Método | URL                   | Descrição                   | Sucesso |
|--------|-----------------------|-----------------------------|---------|
| POST   | `/tutores`            | Cria um tutor               | 201     |
| GET    | `/tutores`            | Lista tutores               | 200     |
| GET    | `/tutores/{id}`       | Busca tutor por id          | 200     |
| GET    | `/tutores/{id}/pets`  | Lista os pets de um tutor   | 200     |
| PUT    | `/tutores/{id}`       | Atualiza um tutor           | 200     |
| DELETE | `/tutores/{id}`       | Exclui um tutor sem pets    | 204     |

### Pets

| Método | URL                    | Descrição                        | Sucesso |
|--------|------------------------|----------------------------------|---------|
| POST   | `/pets`                | Cria um pet                      | 201     |
| GET    | `/pets`                | Lista pets                       | 200     |
| GET    | `/pets/{id}`           | Busca pet por id                 | 200     |
| GET    | `/pets/{id}/consultas` | Histórico de consultas do pet    | 200     |
| PUT    | `/pets/{id}`           | Atualiza um pet                  | 200     |
| DELETE | `/pets/{id}`           | Exclui um pet sem consultas      | 204     |

### Consultas

| Método | URL                         | Descrição                     | Sucesso |
|--------|-----------------------------|-------------------------------|---------|
| POST   | `/consultas`                | Agenda uma consulta           | 201     |
| GET    | `/consultas`                | Lista consultas               | 200     |
| GET    | `/consultas/{id}`           | Busca consulta por id         | 200     |
| PUT    | `/consultas/{id}`           | Remarca/altera (se AGENDADA)  | 200     |
| PATCH  | `/consultas/{id}/realizar`  | Marca como REALIZADA          | 200     |
| PATCH  | `/consultas/{id}/cancelar`  | Marca como CANCELADA          | 200     |

Exemplos prontos de requisição em [`requisicoes.http`](requisicoes.http).

## Regras de negócio

- E-mail de tutor é único (409).
- Pet só é criado se o `tutorId` existir; consulta só se o `petId` existir (404).
- Data de nascimento do pet não pode ser futura; consulta só pode ser agendada no futuro (400).
- Não é possível excluir tutor com pets, nem pet com consultas (409).
- Só consulta `AGENDADA` pode ser alterada, realizada ou cancelada (409).
- Consulta não é excluída, é cancelada (o histórico fica preservado).

## Formato de erro

```json
{
  "status": 400,
  "message": "Erro de validação",
  "path": "/tutores",
  "timestamp": "2026-11-03T16:40:00",
  "errors": {
    "email": "E-mail inválido",
    "telefone": "O telefone deve ter 10 ou 11 dígitos (só números, com DDD)"
  }
}
```

## Próximas etapas

- [x] Banco de dados PostgreSQL com Docker e Spring Data JPA
- [ ] Autenticação com Spring Security + JWT
- [ ] Versionamento com Git e publicação no GitHub
