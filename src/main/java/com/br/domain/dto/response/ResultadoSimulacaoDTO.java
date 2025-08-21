package com.br.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ResultadoSimulacaoDTO(
        @JsonProperty("tipo")
        String tipo,

        @JsonProperty("parcelas")
        List<ParcelaDTO> parcelas
) {}