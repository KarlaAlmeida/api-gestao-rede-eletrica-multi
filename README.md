# Sistema de Gerenciamento de Manutenção de Rede Elétrica

Este projeto é um sistema para gerenciar ativos, técnicos, ocorrências e ordens de serviço em uma rede de distribuição de energia elétrica.

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

1.  **Construir o projeto**:
    Navegue até a pasta `karla-parent` e execute o comando Maven:
    ```bash
    mvn clean install
    ```

2.  **Executar a aplicação**:
    Após a compilação, execute o arquivo JAR gerado:
    ```bash
    java -jar main-app/target/main-app-0.0.1-SNAPSHOT.jar
    ```
