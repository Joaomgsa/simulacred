package com.br.domain.dto.response;

import java.math.BigDecimal;

public record SimulacaoListaResponseDTO(String idSimulacao,
                                        BigDecimal valorDesejado,
                                        Integer prazo,
                                        BigDecimal valorTotalParcelas) {

}
