# Hackathon 2025

Sejam bem-vindos ao Hackathon 2025\! [cite\_start]Estamos empolgados em iniciar esta jornada de inovação e colaboração com vocês[cite: 2, 3]. [cite\_start]Este é o ponto de partida para que vocês apresentem suas ideias e potencial[cite: 5].

## Sobre a Etapa

  * [cite\_start]O objetivo desta fase é avaliar o conhecimento técnico e estratégico dos participantes, bem como sua habilidade de propor soluções inovadoras para desafios da CAIXA[cite: 11].
  * [cite\_start]A participação é exclusiva para os inscritos no Hackathon[cite: 12].
  * [cite\_start]O desafio é à distância, com prazo de 18 a 22 de agosto[cite: 6, 13].
  * [cite\_start]Esta etapa é classificatória e eliminatória[cite: 14].
  * [cite\_start]A avaliação levará em conta a aderência ao tema, inovação e a aplicabilidade da solução proposta[cite: 15].
  * [cite\_start]A pontuação máxima é 100 pontos e a mínima é 50 pontos, sendo que candidatos que não alcançarem a pontuação mínima serão eliminados[cite: 17, 18].
  * [cite\_start]Os melhores classificados serão convocados para a próxima etapa, conforme o ranking de classificação e o número de vagas a ser definido pelo grupo diretivo[cite: 19, 20].

## Orientações sobre o Entregável

  * [cite\_start]**Formato do nome do arquivo:** NOME DO CANDIDATO\_TEMA DA INSCRIÇÃO em formato PDF[cite: 23]. [cite\_start]Para o desafio de Back-end, o arquivo deve ser obrigatoriamente NOME DO CANDIDATO\_TEMA DA INSCRIÇÃO.ZIP[cite: 24].
  * [cite\_start]**Tamanho máximo:** 2MB[cite: 25].
  * [cite\_start]**Prazo de envio:** até 22/08 às 23h59[cite: 26].
  * [cite\_start]**Canal de envio:** Plataforma SIPSI ([https://sipsi.caixa](https://www.google.com/search?q=https://sipsi.caixa)) ou, para empregados afastados (exceto LTS e LAT), para cepes05@caixa.gov.br[cite: 27].
  * [cite\_start]Ao enviar o desafio, o participante declara que o conteúdo é original, de sua autoria e não viola direitos autorais ou confidencialidade de terceiros[cite: 28].

## Canal de Dúvidas

[cite\_start]Dúvidas podem ser enviadas via atendimentopessoas.caixa/[cite: 32, 33, 34].

## Desafios

### Cibersegurança

**Desafio Técnico - Identificação de vulnerabilidades**

[cite\_start]O objetivo é fortalecer a cibersegurança da CAIXA, identificando vulnerabilidades antes que possam ser exploradas[cite: 38]. [cite\_start]Atuando como um hacker ético da CAIXA, você deve trabalhar para garantir a disponibilidade, integridade e confidencialidade dos sistemas, pilares que devem ser considerados na identificação de vulnerabilidades[cite: 39, 40].

**Objetivo do Desafio**
[cite\_start]Escolher um sistema web ou aplicativo da CAIXA e identificar vulnerabilidades de cibersegurança[cite: 42]. Para cada vulnerabilidade, você deve:

1.  [cite\_start]Informar a sigla e a URL do sistema escolhido[cite: 43].
2.  [cite\_start]Detalhar os passos para encontrar a falha de segurança[cite: 44].
3.  [cite\_start]Descrever a solução para a vulnerabilidade[cite: 45].

**Requisitos Técnicos**
[cite\_start]As vulnerabilidades encontradas devem ser categorizadas de acordo com o OWASP Top 10[cite: 47].

**Critérios de Avaliação**

  * [cite\_start]Confirmação da vulnerabilidade a partir dos passos informados[cite: 49].
  * [cite\_start]Proposta de solução viável[cite: 50].

### Back-end

**Desafio Técnico - Simulação de Crédito**

[cite\_start]Você terá acesso a recursos e referências para realizar o desafio[cite: 54]. [cite\_start]A pesquisa, leitura, compreensão do desafio e a elaboração da solução fazem parte da avaliação[cite: 55]. [cite\_start]O desafio é disponibilizar uma solução que permita a simulação de empréstimo para qualquer pessoa ou sistema, por meio de uma API[cite: 58, 59, 60]. [cite\_start]A API deve ser desenvolvida em Java 17+ ou C\# (Dotnet) 8+[cite: 62].

**Requisitos da API:**

  * [cite\_start]Receber um envelope JSON via chamada à API com a solicitação de simulação de empréstimo[cite: 63].
  * [cite\_start]Consultar informações parametrizadas em uma tabela de banco de dados SQL Server[cite: 64].
  * [cite\_start]Validar os dados de entrada com base nos parâmetros de produtos do banco de dados[cite: 65].
  * [cite\_start]Filtrar o produto adequado aos parâmetros de entrada[cite: 66].
  * [cite\_start]Realizar os cálculos para os sistemas de amortização SAC e PRICE[cite: 68].
  * [cite\_start]Retornar um envelope JSON com o nome do produto e os resultados da simulação para ambos os sistemas de amortização[cite: 69].
  * [cite\_start]Gravar o envelope JSON no Eventhub[cite: 69].
  * [cite\_start]Persistir a simulação realizada em banco local[cite: 72].
  * [cite\_start]Criar um endpoint para retornar todas as simulações[cite: 73].
  * [cite\_start]Criar um endpoint para retornar os valores simulados por produto e por dia[cite: 74].
  * [cite\_start]Criar um endpoint para retornar dados de telemetria (volumes e tempos de resposta)[cite: 75].
  * [cite\_start]Disponibilizar o código fonte, com todas as evidências, em formato zip[cite: 76].
  * [cite\_start]Incluir no projeto todos os arquivos para execução via container (dockerfile / Docker compose)[cite: 77].

**Links e Referências**

  * [cite\_start]**O que é API:** [https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api) [cite: 80]
  * [cite\_start]**Calculadora SAC e Price:** [https://calculojuridico.com.br/calculadora-price-sac/](https://calculojuridico.com.br/calculadora-price-sac/) [cite: 82]
  * [cite\_start]**O que é EventHub:** [https://learn.microsoft.com/pt-br/azure/event-hubs/event-hubs-about](https://learn.microsoft.com/pt-br/azure/event-hubs/event-hubs-about) [cite: 84]
  * [cite\_start]**SQL Server:** [https://learn.microsoft.com/pt-br/sql/sql-server/?view=sql-server-ver16](https://learn.microsoft.com/pt-br/sql/sql-server/?view=sql-server-ver16) [cite: 87]
  * **Dados para conexão com banco de dados:**
      * [cite\_start]URL: dbhackathon.database.windows.net [cite: 90]
      * [cite\_start]Porta: 1433 [cite: 92]
      * [cite\_start]DB: hack [cite: 94]
      * [cite\_start]Login: hack [cite: 97]
      * [cite\_start]Senha: Password23 [cite: 99]
      * [cite\_start]Tabela: dbo.Produto [cite: 101]

**Tabela: PRODUTO (dbo)**
| Column Name | Data Type | Allow Nulls |
| :--- | :--- | :--- |
| CO\_PRODUTO | int | No |
| NO\_PRODUTO | varchar(200) | No |
| PC\_TAXA JUROS | numeric(10, 9) | No |
| NU\_MINIMO\_MESES | smallint | No |
| NU\_MAXIMO\_MESES | smallint | Yes |
| VR\_MINIMO | numeric(18, 2) | No |
| VR\_MAXIMO | numeric(18, 2) | Yes |
[cite\_start][cite: 103]

**CREATE TABLE dbo. PRODUTO**

```sql
CREATE TABLE dbo. PRODUTO (
 CO_PRODUTO int NOT NULL primary key,
 NO_PRODUTO varchar(200) NOT NULL,
 PC_TAXA JUROS numeric (10, 9) NOT NULL,
 NU_MINIMO_MESES smallint NOT NULL,
 NU_MAXIMO_MESES smallint NULL,
 VR_MINIMO numeric (18, 2) NOT NULL,
 VR_MAXIMO numeric (18, 2) NULL
);
```

[cite\_start][cite: 104, 105, 106, 107]

**INSERT INTO dbo. PRODUTO**

```sql
INSERT INTO dbo. PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
 NU MINIMO MESES, NU MAXIMO_MESES, VR_MINIMO, VR MAXIMO)
 VALUES (1, 'Produto 1', 0.017900000, 0, 24, 200.00, 10000.00)
```

[cite\_start][cite: 108]

```sql
INSERT INTO dbo. PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
 NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
 VALUES (2, 'Produto 2', 0.017500000, 25, 48, 10001.00, 100000.00)
```

[cite\_start][cite: 109]

```sql
INSERT INTO dbo. PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
 NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAΧΙΜΟ)
 VALUES (3, 'Produto 3', 0.018200000, 49, 96, 100000.01, 1000000.00)
```

[cite\_start][cite: 110]

```sql
INSERT INTO dbo. PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS,
 NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO)
 VALUES (4, 'Produto 4', 0.015100000, 96, null, 1000000.01, null)
```

[cite\_start][cite: 111]

**Arquitetura da Solução**
[cite\_start]A arquitetura da solução inclui a sua API, que interage com uma base de dados SQL e com o Eventhub[cite: 115, 117, 120].

**Dados para conexão com EventHub:**
[cite\_start]`Endpoint=sb://eventhack.servicebus.windows.net/;SharedAccessKeyName=hack;SharedAccessKey=HeHeVaVqyVkn O2FnjQcs2llh/4MUDo4y+AEhKp8z+g=;EntityPath=simulacoes` [cite: 122]

**Modelo de Envelope para Simulação:**

```json
{
 "valorDesejado": 900.00,
 "prazo": 5
}
```

[cite\_start][cite: 125, 126]

**Modelo de Envelope de Retorno para Simulação:**

```json
{
 "idSimulacao": 20180702,
 "codigoProduto": 1,
 "descricaoProduto": "Produto 1",
 "taxaJuros": 0.0179,
 "resultadoSimulacao":
 [
  {
   "tipo": "SAC",
   "parcelas": [
    {
     "numero": 1,
     "valorAmortizacao": 180.00,
     "valorJuros": 16.11,
     "valorPrestacao": 196.11
    },
    {
     "numero": 2,
     "valorAmortizacao": 180.00,
     "valorJuros": 12.89,
     "valorPrestacao": 192.89
    },
    {
     "numero": 3,
     "valorAmortizacao": 180.00,
     "valorJuros": 9.67,
     "valorPrestacao": 189.67
    },
    {
     "numero": 4,
     "valorAmortizacao": 180.00,
     "valorJuros": 6.44,
     "valorPrestacao": 186.44
    },
    {
     "numero": 5,
     "valorAmortizacao": 180.00,
     "valorJuros": 3.22,
     "valorPrestacao": 183.22
    }
   ]
  },
  {
   "tipo": "PRICE",
   "parcelas": [
    {
     "numero": 1,
     "valorAmortizacao": 173.67,
     "valorJuros": 16.11,
     "valorPrestacao": 189.78
    },
    {
     "numero": 2,
     "valorAmortizacao": 176.78,
     "valorJuros": 13.00,
     "valorPrestacao": 189.78
    },
    {
     "numero": 3,
     "valorAmortizacao": 179.94,
     "valorJuros": 9.84,
     "valorPrestacao": 189.78
    },
    {
     "numero": 4,
     "valorAmortizacao": 183.16,
     "valorJuros": 6.62,
     "valorPrestacao": 189.78
    },
    {
     "numero": 5,
     "valorAmortizacao": 186.44,
     "valorJuros": 3.34,
     "valorPrestacao": 189.78
    }
   ]
  }
 ]
}
```

[cite\_start][cite: 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 170, 171, 172, 173]

**Modelo de Chamada para Listar Simulações:**

```json
{
 "pagina":1,
 "qtdRegistros": 404,
 "qtdRegistrosPagina": 200,
 "registros":[
  {
   "idSimulacao": 20180702,
   "valorDesejado": 900.00,
   "prazo": 5,
   "valorTotalParcelas": 1243.28
  }
 ]
}
```

[cite\_start][cite: 194, 195, 196, 197, 199, 203, 204, 205, 206, 201]

**Modelo de Chamada para Retornar Volume Simulado por Produto e por Dia:**

```json
{
 "dataReferencia": "2025-07-30",
 "simulacoes": [{
  "codigoProduto":1,
  "descricaoProduto": "Produto 1",
  "taxaMedia Juro": 0.189,
  "valorMedioPrestacao": 300.00,
  "valorTotalDesejado": 12047.47,
  "valorTotalCredito": 16750.00
 }]
}
```

[cite\_start][cite: 210, 211, 212, 213, 214, 215, 216, 217, 218, 219]

**Modelo de Chamada para Retornar Dados de Telemetria:**

```json
{
 "dataReferencia": "2025-07-30",
 "listaEndpoints":
 [
  {
   "nomeApi": "Simulacao",
   "qtdRequisicoes": 135,
   "tempoMedio": 150, // em milisegundos,
   "tempoMinimo": 23,
   "tempoMaximo": 860,
   "percentualSucesso": 0.98 // qtd de retorno 200 com relacao ao total
  }
 ]
}
```

[cite\_start][cite: 223, 224, 225, 226, 227, 228, 229, 230, 231, 236, 233, 234, 235]


# Cálculo de Empréstimo com Tabela SAC e Tabela PRICE

[cite_start]Com base no documento fornecido, a tabela `dbo.PRODUTO` contém os parâmetros necessários para o cálculo de empréstimos[cite: 101, 102, 103]. [cite_start]O cálculo deve ser feito utilizando o `valorDesejado` e o `prazo` (número de meses) como dados de entrada[cite: 58, 59, 63].

[cite_start]Primeiro, é necessário consultar a tabela `dbo.PRODUTO` no banco de dados para encontrar o produto de crédito que corresponde ao prazo da simulação[cite: 64, 66, 101]. [cite_start]O documento exemplifica diferentes faixas de meses (`NU_MINIMO_MESES` e `NU_MAXIMO_MESES`) que determinam o produto e a taxa de juros (`PC_TAXA_JUROS`) a ser utilizada[cite: 103, 106].
* [cite_start]**Prazo de 0 a 24 meses:** a taxa de juros é `0.017900000` (Produto 1)[cite: 108].
* [cite_start]**Prazo de 25 a 48 meses:** a taxa de juros é `0.017500000` (Produto 2)[cite: 109].
* [cite_start]**Prazo de 49 a 96 meses:** a taxa de juros é `0.018200000` (Produto 3)[cite: 110].
* [cite_start]**Prazo de 96 meses ou mais:** a taxa de juros é `0.015100000` (Produto 4)[cite: 111].

[cite_start]O documento menciona que para o cálculo devem ser utilizados os sistemas de amortização SAC e PRICE[cite: 68]. [cite_start]O documento não fornece as fórmulas, mas sugere uma calculadora jurídica como referência[cite: 82]. [cite_start]O resultado da simulação deve ser um envelope JSON contendo o nome do produto e os resultados para ambos os sistemas de amortização[cite: 69].

## Exemplo de Cálculo (5 parcelas)

[cite_start]O documento apresenta um modelo de retorno de simulação para um valor de R$900,00 com 5 parcelas, utilizando uma taxa de juros de `0.0179`[cite: 125, 133].

### Tabela SAC (Sistema de Amortização Constante)
[cite_start]No sistema SAC, a amortização é fixa e os juros decrescem a cada parcela[cite: 137]. [cite_start]O valor da prestação é a soma da amortização e dos juros[cite: 143, 144, 145].

| Parcela | Amortização | Juros | Prestação |
|---|---|---|---|
| 1 | [cite_start]R$ 180,00 [cite: 143] | [cite_start]R$ 16,11 [cite: 144] | [cite_start]R$ 196,11 [cite: 145] |
| 2 | [cite_start]R$ 180,00 [cite: 147] | [cite_start]R$ 12,89 [cite: 148] | [cite_start]R$ 192,89 [cite: 149] |
| 3 | [cite_start]R$ 180,00 [cite: 151] | [cite_start]R$ 9,67 [cite: 152] | [cite_start]R$ 189,67 [cite: 153] |
| 4 | [cite_start]R$ 180,00 [cite: 155] | [cite_start]R$ 6,44 [cite: 156] | [cite_start]R$ 186,44 [cite: 157] |
| 5 | [cite_start]R$ 180,00 [cite: 160] | [cite_start]R$ 3,22 [cite: 161] | [cite_start]R$ 183,22 [cite: 162] |

### Tabela PRICE (Sistema de Amortização Francês)
[cite_start]No sistema PRICE, a prestação é fixa e a amortização e os juros variam[cite: 163, 164]. [cite_start]A amortização aumenta e os juros diminuem a cada parcela, mantendo a prestação constante[cite: 167, 168, 174].

| Parcela | Amortização | Juros | Prestação |
|---|---|---|---|
| 1 | [cite_start]R$ 173,67 [cite: 167] | [cite_start]R$ 16,11 [cite: 168] | [cite_start]R$ 189,78 [cite: 174] |
| 2 | [cite_start]R$ 176,78 [cite: 176] | [cite_start]R$ 13,00 [cite: 177] | [cite_start]R$ 189,78 [cite: 178] |
| 3 | [cite_start]R$ 179,94 [cite: 180] | [cite_start]R$ 9,84 [cite: 181] | [cite_start]R$ 189,78 [cite: 182] |
| 4 | [cite_start]R$ 183,16 [cite: 184] | [cite_start]R$ 6,62 [cite: 185] | [cite_start]R$ 189,78 [cite: 186] |
| 5 | [cite_start]R$ 186,44 [cite: 188] | [cite_start]R$ 3,34 [cite: 189] | [cite_start]R$ 189,78 [cite: 190] |