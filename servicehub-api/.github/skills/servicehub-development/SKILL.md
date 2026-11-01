---
name: servicehub-development
description: Desenvolve e evolui o ServiceHub API usando Java 21 e Spring Boot, priorizando recursos nativos do ecossistema Spring e evitando boilerplate, código manual repetitivo e abstrações desnecessárias.
---

# ServiceHub Development Skill
Priorize o uso idiomático do Spring Boot e de suas abstrações para reduzir boilerplate. Não implemente manualmente funcionalidades que Spring MVC, Spring Data JPA, Bean Validation, Dependency Injection, JPA/Hibernate, Flyway ou Lombok já fornecem adequadamente. Escreva apenas o código necessário para as regras de negócio e para a organização do projeto.

## Objetivo

Desenvolver o ServiceHub API de forma incremental, simples e profissional.

A prioridade é utilizar corretamente os recursos fornecidos pelo Spring Boot e seu ecossistema.

O objetivo é escrever o mínimo de código necessário para uma solução correta, organizada e sustentável.

Utilizar:

- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Bean Validation
- Lombok para reduzir boilerplate
- Docker Compose
- Spring Security
- JWT
- OpenAPI / Swagger

---

# Regra fundamental

Antes de escrever código manual, verificar se o Spring Boot, Spring Framework, Spring Data ou uma dependência já utilizada fornece uma solução adequada.

Sempre preferir uma solução baseada no framework quando ela reduzir:

- código repetitivo
- complexidade
- manutenção
- possibilidade de erros

---

# Preferências técnicas

## CRUD

Utilizar JpaRepository.

Não criar:

- DAO manual
- implementação manual de CRUD
- SQL manual para operações simples

Preferir:

save()
findAll()
findById()
deleteById()

---

## Injeção de dependência

Utilizar injeção por construtor.

Quando Lombok estiver disponível:

@RequiredArgsConstructor

Evitar:

new Service()
new Repository()

---

## Controllers

Utilizar:

@RestController
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping

Controllers devem ser pequenos.

Eles não devem conter regras de negócio.

---

## Validação

Utilizar:

@Valid
@NotBlank
@NotNull
@Email
@Size
@Positive

Não criar validações manuais repetitivas.

---

## Persistência

Utilizar:

@Entity
@Id
@GeneratedValue
@ManyToOne
@OneToMany
@JoinColumn

Utilizar os relacionamentos do JPA quando adequados.

---

## Exceções

Utilizar:

@RestControllerAdvice

Centralizar tratamento de erros comuns.

Evitar repetir try/catch nos Controllers.

---

## Banco de dados

Utilizar PostgreSQL e Flyway.

Criar migrations incrementais.

Não modificar migrations já aplicadas.

---

# Estrutura

Utilizar somente os pacotes necessários:

controller
service
repository
model
dto
exception
config
security

Não criar todos os pacotes antecipadamente se ainda não forem utilizados.

---

# Processo para implementar um recurso

Para cada recurso:

1. Analisar o projeto atual.
2. Verificar o que já existe.
3. Criar ou atualizar a migration.
4. Criar a Entity.
5. Criar Repository usando JpaRepository.
6. Criar Service com regras de negócio.
7. Criar Controller REST.
8. Criar DTOs somente se necessário.
9. Adicionar validação.
10. Testar a aplicação.

Não criar arquivos sem função real.

---

# Exemplo de arquitetura desejada

Para Provider:

Provider.java
ProviderRepository.java
ProviderService.java
ProviderController.java

Para Client:
Client.java
ClientRepository.java
ClientService.java
ClientController.java

Criar arquivos adicionais apenas se trouxerem benefício real.

---


# Princípio de simplicidade

Preferir:

Controller -> Service -> Repository

Evitar criar camadas intermediárias apenas por padrão arquitetural.

Não utilizar patterns ou abstrações apenas porque são considerados "avançados".

---

# Ao receber uma solicitação

Sempre:

1. Analisar os arquivos atuais.
2. Reutilizar a estrutura existente.
3. Identificar o menor conjunto de alterações necessário.
4. Implementar utilizando Spring Boot.
5. Evitar boilerplate.
6. Verificar compilação.
7. Informar como testar.

---

# Proibido fazer sem necessidade

Não criar:

- DAOs manuais
- CRUD manual
- SQL manual para CRUD simples
- factories sem necessidade
- builders manuais quando não necessários
- interfaces duplicadas
- classes utilitárias desnecessárias
- getters e setters manuais quando Lombok for adequado
- try/catch repetido em Controllers
- camadas extras sem responsabilidade clara

---

# Resultado esperado

O código deve ser um projeto Spring Boot idiomático.

Aproveitar:

- Spring Boot Auto Configuration
- Spring MVC
- Spring Data JPA
- Bean Validation
- Flyway
- Dependency Injection
- JPA/Hibernate
- Lombok
- Tomcat

A solução deve ser simples, clara e adequada para um projeto acadêmico.