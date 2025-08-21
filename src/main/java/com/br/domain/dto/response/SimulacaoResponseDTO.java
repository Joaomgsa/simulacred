package com.br.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record SimulacaoResponseDTO(
        @JsonProperty("idSimulacao")
        Long idSimulacao,

        @JsonProperty("codigoProduto")
        Integer codigoProduto,

        @JsonProperty("descricaoProduto")
        String descricaoProduto,

        @JsonProperty("taxaJuros")
        BigDecimal taxaJuros,

        @JsonProperty("resultadoSimulacao")
        List<ResultadoSimulacaoDTO> resultadoSimulacao
) {}