package com.br.domain.dto.response;

import java.util.List;

public record PaginatedResponseDTO<T>(
        int pagina,
        int qtdRegistros,
        int qtdRegistrosPagina,
        List<T> registros
) {}
