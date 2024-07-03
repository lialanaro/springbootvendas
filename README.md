# Projeto vendas (CRUD) com springboot 

Projeto desenvolvido em Java para cadastro de clientes, produtos,
pedidos em um sistema de vendas.
<br>
API conta com um cadastro de usuário e método de autenticação JWT.

## Tecnologias utilizadas:
* <b> Java 8: </b> Linguagem de programação utilizada no projeto;
* <b> Spring Boot(2.2.4.RELEASE): </b> Framework para a criação de aplicações web;
* <b> Swagger: </b>Ferramenta para documentação de API;
* <b> H2:</b> Banco de dados em memória utilizado no projeto;  
* <b> JWT (JSON Web Token): </b> Utilizado para autenticação e autorização do uso da API;
* <b> Spring Boot Test: </b> Ferramenta para realização de testes Integrados; e
* <b> Maven:</b> Ferramenta de automação de build e gerenciamento de dependências.


### Documentação da API com Swagger
O Swagger pode ser acessado em 'http://localhost:8080/swagger-ui.html' após iniciar 
a aplicação. 

## Como usar a API:
Primeiramente deve ser feito o cadastro de um usuário através do endpoint /api/usuarios,
que, autenticado via Login e senha no endpoint api/usuarios/auth,
recebe um token JWT que deverá ser passado como header no formato Bearer.
Após o JWT, você terá acesso ao core da aplicação, que envolve o cadastro de clientes,
produtos e pedidos.

## Persistência dos dados:
Como o banco de dados H2 é em memória, para a manutenção dos dados recomenda-se
a utilização de um banco de dados como por exemplo: Postgres.
A importância disto é que o cadastro de usuário ADMIN deve ser feita a mão por 
questões de segurança, e apenas ADMIN pode manipular a API de produtos.


## Instalação:

### Clone o repositório

```bash
git clone https://github.com/lialanaro/springbootvendas.git
cd springbootvendas
mvn clean install
java -jar target/springbootvendas-0.0.1-SNAPSHOT.jar

```

## Nota:
Este projeto foi desenvolvido como parte de um curso da Udemy.
Para mais informações sobre o curso, acesse: [Spring Boot Expert: JPA, RESTFul API, Security, JWT](https://www.udemy.com/course/spring-boot-expert/?start=1#overview).



