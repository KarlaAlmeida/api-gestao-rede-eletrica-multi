# Sistema de Gestão de Manutenção de Rede Elétrica

Este projeto é um sistema de back-end para o gerenciamento de operações de manutenção em uma rede elétrica. Ele foi desenvolvido como parte de uma refatoração estratégica para uma arquitetura multi-módulo, visando maior organização, desacoplamento e clareza na separação de responsabilidades.

## Arquitetura Multi-módulo

A arquitetura do projeto foi desenhada para isolar diferentes domínios de responsabilidade em módulos Maven independentes. Essa abordagem, combinada com o uso estratégico de Data Transfer Objects (DTOs), resulta em um sistema mais robusto, escalável e fácil de manter.

A estrutura é dividida nos seguintes módulos:

### 1. `comman-domain`
Este módulo é o coração do sistema. Ele contém as entidades de domínio principais, que modelam os conceitos de negócio essenciais para a gestão de manutenção.

- **Responsabilidades**:
  - Definir as entidades de domínio (`Ativo`, `Ocorrencia`, `OrdemServico`, `Tecnico`, etc.).
  - Centralizar as regras de negócio fundamentais e enumerações.
- **Propósito**: Servir como uma biblioteca de domínio compartilhada, garantindo consistência em todo o sistema.

### 2. `external-api`
Este módulo é responsável por toda a comunicação com serviços externos. Ele abstrai os detalhes de integração, fornecendo clientes `OpenFeign` que podem ser facilmente injetados em outros módulos.

- **Responsabilidades**:
  - Definir e configurar clientes Feign para APIs de terceiros.
  - Atualmente, integra-se com:
    - **ViaCEP**: Para consulta de endereços a partir de um CEP.
    - **OpenStreetMap**: Para obtenção de dados de geolocalização.
- **Propósito**: Isolar as dependências externas, facilitando a substituição ou a manutenção das integrações sem impactar a lógica de negócio principal.

### 3. `main-app`
Este é o módulo principal da aplicação. Ele contém a lógica de orquestração, expõe a API REST para os consumidores (front-end, aplicativos móveis, etc.) e serve como o ponto de entrada executável do sistema.

- **Responsabilidades**:
  - Implementar os controladores REST (`@RestController`).
  - Conter os serviços da aplicação que orquestram as operações.
  - Configurar e executar a aplicação Spring Boot.
- **Propósito**: Unir os outros módulos para entregar a funcionalidade completa do sistema.

## Tecnologias Utilizadas

*   **Java**: 21
*   **Spring Boot**: 3.2.5
*   **Spring Cloud**: 2025.0.0
*   **Spring Cloud OpenFeign**: Para a criação de clientes REST declarativos.
*   **Maven**: Como ferramenta de build e gerenciamento de dependências.


## Segurança e Controle de Acesso

A segurança da API é implementada utilizando Spring Security com autenticação **HTTP Basic** e controle de acesso baseado em papéis (Roles).

### Papéis (Roles)

Existem dois papéis de usuário definidos no sistema:

-   `ADMIN`: Possui acesso total a todos os recursos da API.
-   `USER`: Possui acesso limitado, focado principalmente em consulta e registro de novas ocorrências.

### Autenticação

A autenticação é feita via HTTP Basic. As credenciais padrão estão configuradas em `SecurityConfig.java` e salvas em memória:

-   **Usuário Admin**:
    -   **Username**: `admin`
    -   **Password**: `adminPass`
-   **Usuário Comum**:
    -   **Username**: `user`
    -   **Password**: `userPass`

### Controle de Acesso

O controle de acesso é implementado de duas formas distintas, dependendo do recurso:

#### 1. Controle por Anotações (`@PreAuthorize`)

Os controllers `AtivoController` e `TecnicoController` utilizam anotações de segurança em nível de método para restringir o acesso.

-   **Endpoints de Ativos (`/api/ativos`) e Técnicos (`/api/tecnicos`)**:
    -   **`ADMIN`**: Acesso total (GET, POST, PUT, PATCH, DELETE).
    -   **`USER`**: Acesso somente leitura (GET).

**Exemplo de anotação em `AtivoController`:**

```java
@PostMapping
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<AtivoResponseDTO> incluir(@Valid @RequestBody AtivoRequestDTO ativoDTO) {
    // ...
}

@GetMapping(value = "/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<AtivoResponseDTO> obterPorId(@PathVariable Integer id){
    // ...
}
```

#### 2. Controle por URL (Request Matching)

Os controllers `OcorrenciaController` e `OrdemServicoController` têm suas permissões definidas centralizadamente em `SecurityConfig.java`, através do mapeamento de rotas (URLs).

-   **Endpoints de Ocorrências (`/api/ocorrencias`)**:
    -   **`ADMIN`**: Acesso total (GET, POST, PUT, PATCH, DELETE).
    -   **`USER`**: Pode criar (POST) e visualizar (GET) ocorrências. Não pode alterar ou excluir.

-   **Endpoints de Ordens de Serviço (`/api/ordem-servico`)**:
    -   **`ADMIN`**: Acesso total (GET, POST, PUT, PATCH, DELETE).
    -   **`USER`**: Acesso somente leitura (GET).

**Exemplo de configuração em `SecurityConfig.java`:**

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // ...
        .authorizeHttpRequests(authorize -> authorize
            // ...
            .requestMatchers(HttpMethod.POST, "api/ocorrencias" ).hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.PUT, "api/ocorrencias/{id}" ).hasAnyRole("ADMIN")
            // ...
        )
        .httpBasic(withDefaults());
    return http.build();
}
```

## Como Executar

1.  **Pré-requisitos**:
    *   JDK 21 ou superior instalado.
    *   Apache Maven 3.8 ou superior instalado.

2.  **Compilação**:
    Navegue até o diretório raiz (`karla-parent`) e execute o seguinte comando para compilar todos os módulos:
    ```bash
    mvn clean install
    ```

3.  **Execução**:
    Após a compilação bem-sucedida, inicie a aplicação principal com o seguinte comando:
    ```bash
    java -jar main-app/target/main-app-0.0.1-SNAPSHOT.jar
    ```

A aplicação estará disponível em `http://localhost:8081`.
