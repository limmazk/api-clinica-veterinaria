# API Clínica Veterinária

API REST para cadastro de tutores, pets e agendamento de consultas.

> **Estado atual:** os dados ficam **em memória** (num `Map` dentro de cada Repository) e são perdidos quando a aplicação reinicia. Banco de dados e autenticação JWT serão adicionados nas próximas etapas.

## Tecnologias

- Java 17
- Spring Boot 3.5 (Web, Validation)
- Maven
- JUnit 5 + Mockito


## Modelo


- **Tutor**: nome, email (único), telefone
- **Pet**: nome, especie (`CACHORRO`, `GATO`, `PASSARO`, `OUTRO`), raca, dataNascimento, tutor
- **Consulta**: pet, dataHora, motivo, valor, status (`AGENDADA`, `REALIZADA`, `CANCELADA`)

## Estrutura

```
controller  -> recebe a requisição HTTP e devolve ResponseEntity
service     -> regras de negócio
repository  -> acesso aos dados (hoje: memória; depois: JPA + banco)
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

## Regras de negócio

- E-mail de tutor é único (409).
- Pet só é criado se o `tutorId` existir; consulta só se o `petId` existir (404).
- Data de nascimento do pet não pode ser futura; consulta só pode ser agendada no futuro (400).
- Não é possível excluir tutor com pets, nem pet com consultas (409).
- Só consulta `AGENDADA` pode ser alterada, realizada ou cancelada (409).
- Consulta não é excluída, é cancelada (o histórico fica preservado).

