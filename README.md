# Fórum API

Uma API REST para um fórum de discussão construída com Spring Boot e Spring Security, feita durante o curso [ONE - Oracle Next Education](https://www.oracle.com/br/education/oracle-next-education/).

## Funcionalidades

- Autenticação de usuários com JWT
- Operações CRUD para tópicos do fórum
- Organização por cursos
- Paginação e validação de entrada
- Migrações de banco de dados com Flyway

## Tecnologias

- Java 21
- Spring Boot 3.5.4
- Spring Security & JPA
- MySQL
- Maven

## Como Executar

1. Clone o repositório
2. Configure o banco de dados MySQL
3. Configure o `application.properties`
4. Execute: `mvn spring-boot:run`

## Endpoints da API

- `POST /login` - Obter token JWT
- `GET /topicos` - Listar tópicos (com paginação)
- `POST /topicos` - Criar tópico
- `GET /topicos/{id}` - Obter tópico
- `PUT /topicos/{id}` - Atualizar tópico
- `DELETE /topicos/{id}` - Desativar tópico

## Banco de Dados

Tabelas: `usuarios`, `cursos`, `topicos`
## Licença

MIT
