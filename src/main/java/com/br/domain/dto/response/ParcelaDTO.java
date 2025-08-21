package com.br.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ParcelaDTO(
        @JsonProperty("numero")
        Integer numero,

        @JsonProperty("valorAmortizacao")
        BigDecimal valorAmortizacao,

        @JsonProperty("valorJuros")
        BigDecimal valorJuros,

        @JsonProperty("valorPrestacao")
        BigDecimal valorPrestacao
) {}