# LiterAlura - Challenge Oracle Next Education

Este projeto é um catálogo de livros que consome dados da API Gutendex, processa informações em formato JSON e as armazena em um banco de dados relacional PostgreSQL.

## Tecnologias Utilizadas
* **Java 17**
* **Spring Boot 3.2.3**
* **Spring Data JPA**
* **PostgreSQL**
* **Jackson** (para manipulação de JSON)

## Funcionalidades
* Buscar livros pelo título através da API.
* Listar todos os livros salvos no banco de dados.
* Listar autores registrados.
* Filtrar autores vivos em um determinado ano.
* Filtrar livros por idioma (en, es, fr, pt).

## Como executar
1. Clone o repositório.
2. Configure o banco de dados PostgreSQL no arquivo `application.properties`.
3. Certifique-se de ter o banco de dados chamado `literalura` criado.
4. Execute o projeto através da classe `LiteraluraApplication`.
