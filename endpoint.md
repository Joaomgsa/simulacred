# Tarefas para Criar Endpoints da Entidade Simulação

Este documento descreve as tarefas necessárias para implementar endpoints completos baseados na entidade `Simulacao`, seguindo os requisitos do Hackathon 2025 e as melhores práticas de desenvolvimento de APIs REST.

## 📋 Tarefas Principais

### 1. Implementação do Controller/Resource

- [ ] **Criar SimulacaoController**
  - Criar arquivo `src/main/java/com/br/resources/SimulacaoController.java`
  - Implementar anotações JAX-RS (`@Path`, `@ApplicationScoped`, `@Produces`, `@Consumes`)
  - Definir path base como `/simulacoes`
  - Injetar dependência do `SimulacaoService`

### 2. Implementação dos Métodos de Service

- [ ] **Expandir SimulacaoService Interface**
  - Adicionar métodos para CRUD básico
  - Adicionar método `getAllSimulacoes()` - retornar todas as simulações
  - Adicionar método `getSimulacoesPorProdutoEData()` - valores simulados por produto e por dia
  - Adicionar método `getTelemetria()` - dados de telemetria (volumes e tempos de resposta)
  - Adicionar método `criarSimulacao()` - criar nova simulação

- [ ] **Criar SimulacaoServiceImpl**
  - Implementar classe `src/main/java/com/br/application/service/impl/SimulacaoServiceImpl.java`
  - Implementar todos os métodos da interface
  - Injetar `SimulacaoRepository`
  - Adicionar validações de negócio

### 3. DTOs e Modelos de Request/Response

- [ ] **Criar DTOs de Request**
  - Atualizar `SimulacaoRequest` para incluir todos os campos necessários
  - Adicionar validações com Bean Validation (`@NotNull`, `@Valid`, etc.)
  - Implementar validação de valores mínimos/máximos

- [ ] **Criar DTOs de Response**
  - Criar `SimulacaoResponse` com todos os campos da entidade
  - Criar `SimulacaoSummaryResponse` para listagens
  - Criar `TelemetriaResponse` para dados de telemetria
  - Criar `SimulacaoPorProdutoResponse` para agrupamentos por produto

- [ ] **Criar DTOs para Resultados Complexos**
  - Implementar modelo para resultados SAC/PRICE conforme especificação do Hackathon
  - Incluir array de parcelas com detalhamento (número, valor amortização, valor juros, valor prestação)

### 4. Endpoints RESTful

#### 4.1 Endpoint: Criar Simulação
- [ ] **POST /simulacoes**
  - Receber `SimulacaoRequest` no body
  - Validar dados de entrada
  - Calcular valores SAC e PRICE
  - Persistir simulação no banco
  - Enviar para EventHub
  - Retornar `SimulacaoResponse` com HTTP 201

#### 4.2 Endpoint: Listar Todas as Simulações
- [ ] **GET /simulacoes**
  - Implementar paginação com parâmetros `page` e `size`
  - Implementar filtros opcionais (produto, data, valor)
  - Retornar lista de `SimulacaoSummaryResponse`
  - Incluir metadados de paginação

#### 4.3 Endpoint: Buscar Simulação por ID
- [ ] **GET /simulacoes/{id}**
  - Validar se ID existe
  - Retornar `SimulacaoResponse` completa
  - Tratamento de erro 404 se não encontrar

#### 4.4 Endpoint: Valores por Produto e Data
- [ ] **GET /simulacoes/por-produto-data**
  - Parâmetros de query: `dataInicio`, `dataFim`, `codigoProduto` (opcional)
  - Agrupar simulações por produto e data
  - Retornar estatísticas (total, média, quantidade)
  - Formato: `SimulacaoPorProdutoResponse[]`

#### 4.5 Endpoint: Dados de Telemetria
- [ ] **GET /simulacoes/telemetria**
  - Retornar volumes de simulações por período
  - Incluir tempos médios de resposta
  - Métricas de uso da API
  - Formato: `TelemetriaResponse`

### 5. Validações e Tratamento de Erros

- [ ] **Implementar Validações**
  - Validar valor desejado contra limites do produto
  - Validar prazo contra limites mínimos/máximos do produto
  - Validar se produto existe e está ativo

- [ ] **Tratamento de Exceções**
  - Criar `ExceptionMapper` personalizado
  - Tratar `ValidationException`
  - Tratar `EntityNotFoundException`
  - Padronizar respostas de erro (formato JSON consistente)

### 6. Integração com EventHub

- [ ] **Configurar Cliente EventHub**
  - Adicionar dependências necessárias no `pom.xml`
  - Configurar connection string no `application.properties`
  - Criar serviço para envio de mensagens

- [ ] **Implementar Envio Assíncrono**
  - Enviar envelope JSON após persistir simulação
  - Implementar retry em caso de falha
  - Log de auditoria para rastreamento

### 7. Cálculos Financeiros

- [ ] **Implementar Calculadora SAC**
  - Criar classe `CalculadoraSAC`
  - Implementar fórmula: amortização constante
  - Calcular juros decrescentes

- [ ] **Implementar Calculadora PRICE**
  - Criar classe `CalculadoraPRICE`
  - Implementar fórmula: prestação constante
  - Calcular amortização crescente

- [ ] **Integrar Cálculos no Service**
  - Executar ambos os cálculos na criação
  - Armazenar resultados na resposta
  - Persistir valores calculados

### 8. Testes

- [ ] **Testes Unitários**
  - Testar `SimulacaoService` com mocks
  - Testar calculadoras financeiras
  - Testar validações de DTOs

- [ ] **Testes de Integração**
  - Testar endpoints com `@QuarkusTest`
  - Testar integração com banco de dados
  - Testar cenários de erro

### 9. Documentação

- [ ] **OpenAPI/Swagger**
  - Adicionar anotações `@Operation`, `@ApiResponse`
  - Documentar parâmetros e schemas
  - Configurar Swagger UI

- [ ] **Documentação Adicional**
  - Atualizar README.md com exemplos de uso
  - Documentar modelos de request/response
  - Incluir exemplos de curl

### 10. Performance e Monitoramento

- [ ] **Otimizações**
  - Implementar cache para consulta de produtos
  - Otimizar queries de banco (índices, joins)
  - Implementar paginação eficiente

- [ ] **Métricas**
  - Configurar métricas do Micrometer
  - Implementar health checks
  - Configurar logs estruturados

## 🔧 Configurações Necessárias

### application.properties
```properties
# EventHub Configuration
eventhub.connection-string=Endpoint=sb://eventhack.servicebus.windows.net/;SharedAccessKeyName=hack;SharedAccessKey=HeHeVaVqyVkn O2FnjQcs2llh/4MUDo4y+AEhKp8z+g=;EntityPath=simulacoes

# Pagination defaults
app.pagination.default-size=20
app.pagination.max-size=100
```

### Dependências Maven
```xml
<!-- EventHub -->
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-messaging-eventhubs</artifactId>
</dependency>

<!-- Metrics -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-micrometer</artifactId>
</dependency>
```

## 🎯 Critérios de Aceitação

- [ ] Todos os endpoints definidos funcionam conforme especificação
- [ ] Validações impedem dados inválidos
- [ ] Cálculos SAC e PRICE estão corretos
- [ ] Integração com EventHub funciona
- [ ] Dados são persistidos corretamente
- [ ] Testes cobrem cenários principais
- [ ] Documentação está completa
- [ ] Performance é adequada (< 500ms para operações simples)

## 📈 Entregáveis

1. Código fonte completo dos endpoints
2. Testes automatizados
3. Documentação da API (Swagger)
4. Exemplos de uso
5. Scripts de teste manual (Postman/curl)

---

**Observação**: Esta lista de tarefas segue os requisitos do Hackathon 2025 e pode ser adaptada conforme necessidades específicas do projeto.