package com.br.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record SimulacaoResponseDTO(
        @JsonProperty("idSimulacao")
        String idSimulacao,

        @JsonProperty("codigoProduto")
        String codigoProduto,

        @JsonProperty("descricaoProduto")
        String descricaoProduto,

        @JsonProperty("taxaJuros")
        BigDecimal taxaJuros,

        @JsonProperty("resultadoSimulacao")
        List<ResultadoSimulacaoDTO> resultadoSimulacao
) {}