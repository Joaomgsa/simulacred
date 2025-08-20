package com.br.domain.dto.response;

import java.math.BigDecimal;

public record ProdutoDTO(
        Long coProduto,
        String noProduto,
        BigDecimal pcTaxaJuros,
        Short nuMinimoParcelas,
        Short nuMaximoParcelas,
        BigDecimal vrMinimo,
        BigDecimal vrMaximo
) {

}

