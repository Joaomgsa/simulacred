package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.Produto;

public interface ParcelaService {

    // Injetar repository generico para acessar os dados das parcelas

    public void calcularParcelas(SimulacaoRequestDTO dto, Produto produto);
}
