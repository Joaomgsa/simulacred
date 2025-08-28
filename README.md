# Projeto de Simulação Financeira

Projeto Spring Boot/Quarkus para simulação de financiamentos com diferentes tipos de amortização (SAC e Price).

## Pré-requisitos

- Java 17+
- Docker e Docker Compose
- Maven 3.8+

## Configuração do Ambiente

### 1. Subindo o Banco de Dados com Docker

Crie um arquivo `docker-compose.yml` na raiz do projeto:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: simulacao-db
    environment:
      POSTGRES_DB: simulacao_db
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./scripts:/docker-entrypoint-initdb.d

volumes:
  postgres_data:
```

**Subir o container:**

```bash
docker-compose up -d
```

### 2. Scripts de Banco de Dados

Crie a pasta `scripts/` na raiz do projeto e adicione os arquivos SQL:

#### `scripts/01-create-tables.sql`

```sql
-- Criação das tabelas
CREATE TABLE produto (
    co_produto BIGSERIAL PRIMARY KEY,
    no_produto VARCHAR(255) NOT NULL,
    pc_taxa_juros DECIMAL(10,4) NOT NULL
);

CREATE TABLE simulacao (
    nu_simulacao BIGSERIAL PRIMARY KEY,
    vr_simulacao DECIMAL(15,2) NOT NULL,
    pr_simulacao INTEGER NOT NULL,
    co_produto BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (co_produto) REFERENCES produto(co_produto)
);

CREATE TABLE parcela_sac (
    id BIGSERIAL PRIMARY KEY,
    nu_simulacao BIGINT NOT NULL,
    nu_parcela INTEGER NOT NULL,
    vr_prestacao DECIMAL(15,2) NOT NULL,
    vr_juros DECIMAL(15,2) NOT NULL,
    vr_amortizacao DECIMAL(15,2) NOT NULL,
    vr_saldo DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (nu_simulacao) REFERENCES simulacao(nu_simulacao)
);

CREATE TABLE parcela_price (
    id BIGSERIAL PRIMARY KEY,
    nu_simulacao BIGINT NOT NULL,
    nu_parcela INTEGER NOT NULL,
    vr_prestacao DECIMAL(15,2) NOT NULL,
    vr_juros DECIMAL(15,2) NOT NULL,
    vr_amortizacao DECIMAL(15,2) NOT NULL,
    vr_saldo DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (nu_simulacao) REFERENCES simulacao(nu_simulacao)
);
```

#### `scripts/02-insert-data.sql`

```sql
-- Inserção de dados de exemplo
INSERT INTO produto (no_produto, pc_taxa_juros) VALUES
('Crédito Consignado', 2.50),
('Financiamento Imobiliário', 8.75),
('Crédito Pessoal', 15.90);

INSERT INTO simulacao (vr_simulacao, pr_simulacao, co_produto) VALUES
(50000.00, 24, 1),
(300000.00, 360, 2),
(15000.00, 12, 3);

-- Exemplo de parcelas SAC para simulação 1
INSERT INTO parcela_sac (nu_simulacao, nu_parcela, vr_prestacao, vr_juros, vr_amortizacao, vr_saldo) VALUES
(1, 1, 2291.67, 1250.00, 2083.33, 47916.67),
(1, 2, 2265.63, 1197.92, 2083.33, 45833.33);

-- Exemplo de parcelas Price para simulação 2
INSERT INTO parcela_price (nu_simulacao, nu_parcela, vr_prestacao, vr_juros, vr_amortizacao, vr_saldo) VALUES
(2, 1, 2640.85, 2187.50, 453.35, 299546.65),
(2, 2, 2640.85, 2184.18, 456.67, 299090.00);
```

### 3. Configuração da Aplicação

#### `application.properties`

```properties
# Configuração do banco de dados
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=admin
quarkus.datasource.password=admin123
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/simulacao_db

# Configuração do Hibernate
quarkus.hibernate-orm.database.generation=none
quarkus.hibernate-orm.log.sql=true

# Configuração da aplicação
quarkus.http.port=8080
quarkus.log.level=INFO
```

## Executando a Aplicação

### 1. Instalar dependências

```bash
mvn clean install
```

### 2. Executar no modo desenvolvimento (Quarkus)

```bash
mvn quarkus:dev
```

Ou alternativamente:

```bash
./mvnw quarkus:dev
```

### 3. Executar em produção

```bash
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

## Endpoints da API

### Listar Simulações (com paginação)
```
GET /api/simulacoes?page=0&size=10
```

### Relatório por Produto e Data
```
GET /api/simulacoes/relatorio?dataReferencia=2024-01-15
```

## Comandos Úteis

### Verificar status do banco
```bash
docker exec -it simulacao-db psql -U admin -d simulacao_db -c "\dt"
```

### Parar os containers
```bash
docker-compose down
```

### Logs da aplicação
```bash
docker-compose logs -f postgres
```

### Rebuild completo
```bash
docker-compose down -v
docker-compose up -d
mvn clean install
mvn quarkus:dev
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/br/
│   │       ├── domain/
│   │       │   ├── dto/
│   │       │   ├── model/
│   │       │   └── repository/
│   │       └── resource/
│   └── resources/
│       └── application.properties
scripts/
├── 01-create-tables.sql
└── 02-insert-data.sql
docker-compose.yml
```

## Tecnologias Utilizadas

- **Quarkus** - Framework Java nativo
- **Hibernate ORM with Panache** - ORM e repositórios
- **PostgreSQL** - Banco de dados relacional
- **Docker** - Containerização
- **Maven** - Gerenciamento de dependências