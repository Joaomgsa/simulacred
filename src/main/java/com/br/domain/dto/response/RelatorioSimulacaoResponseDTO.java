package com.br.domain.dto.response;

import java.time.LocalDate;
import java.util.List;

public record RelatorioSimulacaoResponseDTO(
        LocalDate dataReferencia,
        List<SimulacaoPorProdutoDTO> simulacoes
) {
}
