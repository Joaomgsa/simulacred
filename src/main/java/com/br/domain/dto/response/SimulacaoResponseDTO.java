package com.br.domain.dto.response;

import java.math.BigDecimal;

public record SimulacaoResponseDTO(
        Long idSimulacao,
        String codigoProduto,
        String descrivaoProduto,
        BigDecimal taxaJuros
        ) {
}
