# 🌐 API - Biblioteca

Esta aplicação está sendo desenvolvida como parte das notas do acadêmico [Anderson Reiner Nogueira de Souza](https://github.com/AndersonReiner) do curso barachelado em ciências da computação, na Universidade do Estado de Mato Grosso **UNEMAT**,  no período **2025/01**, com foco na aplicação de boas práticas de desenvolvimento web.

O projeto conta com a orientação dos professores **Antônio Carlos**, responsável pela disciplina de Desenvolvimento Web, e **Mareci Segato**, que ministra a disciplina de Laboratório de Engenharia de Software.

A proposta surgiu como um desafio para desenvolver, parcial ou totalmente, uma aplicação funcional durante as 60 horas da disciplina de Desenvolvimento Web, simulando o ambiente real de produção de um software. Já na disciplina de Laboratório de Engenharia de Software, o foco está na aplicação de metodologias ágeis e na documentação essencial do processo de desenvolvimento.


## 🛠️ Tecnologias Utilizadas

- Java 21 como linguagem principal de desenvolvimento.

- Spring Boot para facilitar a criação da aplicação web com estrutura robusta e produtiva.

- Spring Web para criação dos endpoints REST da API.

- Spring Data JPA para integração e persistência de dados com bancos relacionais.

- PostgreSQL como banco de dados relacional utilizado na aplicação.

- Spring Boot DevTools para facilitar o desenvolvimento com recarregamento automático e melhorias na produtividade.

- Lombok para reduzir a verbosidade do código, gerando automaticamente getters, setters, construtores, entre outros.

- Spring Boot Validation para validação de dados de entrada via anotações.

- Springdoc OpenAPI para geração automática da documentação da API em formato Swagger.

- Spring Boot Starter Test para construção de testes automatizados durante o desenvolvimento.
---

## 🗃️ Repositórios oficiais

- Imagen da aplicação disponível no [Dockerhub](https://hub.docker.com/r/andersonreiner/lib-api)

- Repositório oficial no [GitHub](https://github.com/AndersonReiner/lib-api)

---


## 🧭 Acesse a [Documentação Swagger](https://swagger.io/docs/)

Com a aplicação rodando em um servidor (podendo ser em localhost) acesse a documentação da **API** no endereço abaixo.

[acesse a documentação dos endpoints aqui 👈](http://localhost:8080/swagger-ui/index.html#/)


```http

http://host:port/swagger-ui/index.html#/

```
Exemplo:

```http

http://localhost:8080/swagger-ui/index.html#/

```

## ⚙️ Ambiente de Produção

Como configuarar a aplicação em um servidor:

- Antes de partir para as configurações vamos entender quais informações a aplicação espera em sua inialização:

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

@Value("${CORS_ORIGIN}")
private String corsOrigin;
```

### 📑 Configuração arquivo .env

- Agora vamos configurar um arquivo **.env** contendo as informações sensíoveis sobre a aplicação:
  - Endereço do banco de dados.
  - Nome do banco de dados.
  - Usuário do banco de dados.
  - Senha do banco de dados.
  - Endereço de cors da aplicação.


Modelo:
```.env
DB_URL=jdbc:postgresql://host:port/nome_banco
DB_USERNAME=admin
DB_PASSWORD=root
CORS_ORIGIN=host
```

Exemplo:
```.env
DB_URL=jdbc:postgresql://127.20.0.1:5432/meu-banco
DB_USERNAME=juca
DB_PASSWORD=jucajuca10
CORS_ORIGIN=*  <-indica que está sem restrição para receber requisição.
```
Seguindo......

### 📑 Configuração arquivo Docker Compose

O arquivo **docker-compose.yaml** pode ser estruturado de várias formas, deixarei um modelo de configuração que não necessita de um banco de dados inicalizado no mesmo arquivo docker-compose com aplicação, ou seja, pode ser um banco externo ou interno ao host, podendo estar no mesmo host, sob os cuidados de configuração da network do container do banco.

Modelo:


```docker-compose
version: '3.8'

services:
  app:
    image: andersonreiner/lib-api:version.1.1  // imagem da aplicação no repositório remoto docker hub.
    env_file:
      - .env                                   // arquivo de cinfiguração citado acima 
    ports:
      - "8080:8080"
    restart: always
    networks:
      - <network-do-container-do-banco-de-dados>

networks:
  <network-do-container-do-banco-de-dados>
    external: true

```

### Nível de arquivos em diretório
Após a configuração dos aquivos necessários, eles devem ficar em uma uma estrutura parecida com a mostrada abaixo.

Obs: o arquivo docker-compose configurado acima necessita que o arquivo .env esteja no mesmo nível de diretório para funcionar!

````bash

servidor/
├── diretótio-produção/
│   ├── docker-compose.yml
│   ├── .env
└── outhers folders/

````

agora dentro do diretorio dos arquivos, basta rodar os comandos:

Inicializar a aplicação.

````bash

docker-compose up -d
````

Checar a injeção das variaveis de ambiente pelo docker compose.

```bash

 docker exec -it <nome-do-container>

```

Ativando os logs da aplicação.

```bash

docker compose logs -f
```


