# ServiceHub API - Instruções do Projeto

## Idioma e estilo
Priorize o uso idiomático do Spring Boot e de suas abstrações para reduzir boilerplate. Não implemente manualmente funcionalidades que Spring MVC, Spring Data JPA, Bean Validation, Dependency Injection, JPA/Hibernate, Flyway ou Lombok já fornecem adequadamente. Escreva apenas o código necessário para as regras de negócio e para a organização do projeto.

- Responda e documente em português do Brasil.
- Priorize código simples, idiomático e alinhado às convenções do Spring Boot.
- Evite código excessivo, repetitivo ou desnecessário.
- Não implemente manualmente funcionalidades que o Spring Boot, Spring Framework ou Spring Data já fornecem adequadamente.

---

# Stack

Utilizar:

- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Bean Validation
- Lombok quando reduzir boilerplate
- Docker Compose

Futuramente:

- Spring Security
- JWT
- OpenAPI / Swagger

Não alterar versões ou tecnologias sem necessidade.

---

# Regra principal: usar os benefícios do Spring

Antes de escrever código manual, verificar se o Spring Boot ou suas dependências já oferecem uma solução adequada.

Preferir:

- JpaRepository para CRUD.
- Spring Data JPA para persistência.
- Injeção de dependência por construtor.
- ResponseEntity para respostas HTTP quando necessário.
- @RestController para APIs REST.
- @RequestMapping, @GetMapping, @PostMapping, @PutMapping e @DeleteMapping.
- Bean Validation com @Valid.
- Anotações JPA para mapeamento de entidades.
- Flyway para evolução do banco.
- Tratamento global de exceções com @RestControllerAdvice.
- Lombok apenas para reduzir boilerplate.

Evitar:

- Criar DAOs manuais quando JpaRepository resolver.
- Escrever CRUD SQL manual sem necessidade.
- Criar getters e setters manualmente quando Lombok estiver configurado e for apropriado.
- Criar construtores desnecessários.
- Instanciar Services ou Repositories com new.
- Repetir tratamento de erro em todos os Controllers.
- Criar classes auxiliares sem necessidade.
- Criar camadas extras sem benefício real.
- Fazer abstrações excessivas para um projeto acadêmico.

---

# Arquitetura

Manter uma arquitetura simples:

Controller -> Service -> Repository -> Banco de Dados

Responsabilidades:

## Controller

Responsável por:

- Receber requisições HTTP.
- Validar entrada usando @Valid.
- Chamar o Service.
- Retornar respostas HTTP.

Controllers não devem conter regras complexas de negócio.

## Service

Responsável por:

- Regras de negócio.
- Orquestração entre Controller e Repository.
- Busca e validação de recursos.

Evitar código duplicado.

## Repository

Utilizar Spring Data JPA.

Exemplo preferido:

public interface ProviderRepository
        extends JpaRepository<Provider, Long> {
}

Não criar implementações manuais de CRUD sem necessidade.

## Model

Contém:

- Entidades JPA.
- Enums.
- Relacionamentos entre entidades.

---

# Organização de pacotes

Utilizar:

com.servicehub

├── controller
├── service
├── repository
├── model
├── dto
├── exception
├── config
└── security

Criar pacotes adicionais somente quando houver necessidade real.

Não criar múltiplas classes ou interfaces public no mesmo arquivo Java.

---

# Entidades principais

O sistema possui:

- Provider
- Client
- Contract
- Review
- ServiceCategory

Os relacionamentos devem utilizar JPA.

Não simular relacionamentos manualmente apenas com IDs. Usar uma associação JPA adequada ao modelo.

---

# Persistência

Utilizar Spring Data JPA.

Preferir métodos fornecidos pelo JpaRepository:

- save()
- findAll()
- findById()
- existsById()
- delete()
- deleteById()

Criar query methods apenas quando necessário.

Exemplo:

List<Contract> findByProviderId(Long providerId);

Evitar SQL manual para consultas simples.

---

# Banco de dados

Utilizar PostgreSQL.

Toda alteração estrutural do banco deve ser feita com Flyway.

Migrations:

src/main/resources/db/migration/

Formato:

V1__create_providers_table.sql
V2__create_clients_table.sql
V3__create_contracts_table.sql
V4__create_reviews_table.sql

Nunca alterar uma migration já aplicada.

Para alterações posteriores, criar uma nova migration.

---

# REST

Utilizar substantivos no plural.

Providers:

GET /providers
GET /providers/{id}
POST /providers
PUT /providers/{id}
DELETE /providers/{id}

Clients:

GET /clients
GET /clients/{id}
POST /clients
PUT /clients/{id}
DELETE /clients/{id}

Contracts:

GET /contracts
GET /contracts/{id}
POST /contracts
PUT /contracts/{id}
DELETE /contracts/{id}

Reviews:

GET /reviews
GET /reviews/{id}
POST /reviews
PUT /reviews/{id}
DELETE /reviews/{id}

Sub-recursos somente quando fizer sentido:

GET /providers/{id}/contracts
GET /clients/{id}/contracts
GET /contracts/{id}/reviews

Não colocar verbos nas URIs.

---

# Status HTTP

Utilizar:

GET sucesso:
200 OK

POST sucesso:
201 Created

PUT sucesso:
200 OK

DELETE sucesso:
204 No Content

Recurso inexistente:
404 Not Found

Dados inválidos:
400 Bad Request

Autenticação futura:
401 Unauthorized

Autorização futura:
403 Forbidden

---

# Validação

Utilizar Bean Validation.

Preferir:

@NotBlank
@NotNull
@Email
@Size
@Positive

Utilizar @Valid nos Controllers.

Não criar validação manual repetitiva quando Bean Validation resolver o problema.

---

# Tratamento de exceções

Utilizar tratamento global.

Preferir:

@RestControllerAdvice

Não repetir blocos try/catch em todos os endpoints para tratar erros comuns.

Criar exceções específicas somente quando necessário.

---

# DTOs

Utilizar DTOs para separar a API das entidades quando isso trouxer benefício claro.

Não criar DTOs excessivos apenas por formalidade.

Para cada recurso, preferir uma estrutura simples e coerente.

Exemplo:

ProviderRequest
ProviderResponse

Criar DTOs específicos adicionais somente quando houver necessidade.

---

# Lombok

Utilizar Lombok corretamente configurado para reduzir boilerplate.

Conforme necessário:

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

---

# Injeção de dependência

Preferir injeção por construtor.

Exemplo:

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
}

Não utilizar:

new ProviderService()

Não utilizar injeção manual de dependências.

---

# Código enxuto

Sempre buscar a solução mais simples que utilize corretamente o framework.

Antes de criar uma classe, método ou camada adicional, perguntar:

"Isso é realmente necessário ou o Spring já resolve?"

Evitar:

- Boilerplate.
- Código duplicado.
- Classes de uma única linha sem propósito.
- Interfaces sem necessidade.
- Abstrações prematuras.
- Design patterns apenas para demonstrar patterns.
- Código Java manual quando uma abstração do Spring resolve adequadamente.

---

# Fluxo de trabalho

Antes de implementar:

1. Analisar os arquivos existentes.
2. Verificar se a funcionalidade já existe.
3. Identificar dependências necessárias.
4. Reutilizar código existente quando apropriado.
5. Evitar duplicação.

Ao implementar:

1. Alterar somente os arquivos necessários.
2. Criar o menor número de arquivos necessário.
3. Utilizar recursos do Spring Boot.
4. Manter o código compilável.
5. Não implementar funcionalidades futuras sem solicitação.

Após implementar:

1. Executar ou indicar a execução do Maven.
2. Verificar erros de compilação.
3. Informar como testar.
4. Informar endpoints e exemplos JSON.

---

# Definition of Done

Uma funcionalidade está concluída quando:

- Compila.
- Respeita a arquitetura existente.
- Não duplica código.
- Usa os recursos adequados do Spring Boot.
- Possui validação quando necessária.
- Retorna status HTTP correto.
- Pode ser testada.
- Não introduz complexidade desnecessária.