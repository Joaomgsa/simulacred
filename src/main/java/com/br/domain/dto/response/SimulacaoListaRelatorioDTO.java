package com.br.domain.dto.response;

import java.math.BigDecimal;

public record SimulacaoListaRelatorioDTO(
        String codigo,
        BigDecimal vrSimulacao,
        Integer prSimulacao,
        BigDecimal vrTotalPrestacao
) {
}
