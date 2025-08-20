package com.br.domain.dto.request;

import java.math.BigDecimal;

public record simulacaoRequest(BigDecimal valorDesejado, int prazo) {
}
