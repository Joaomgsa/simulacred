package com.br.domain.dto.response;

import java.math.BigDecimal;

public record SimulacaoPorProdutoDTO(
        Integer codigoProduto,
        String descricaoProduto,
        BigDecimal taxaMediaJuro,
        BigDecimal valorMedioPrestacao,
        BigDecimal valorTotalDesejado,
        BigDecimal valorTotalCredito
) {
}
