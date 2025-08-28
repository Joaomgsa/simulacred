package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ParcelaSacService {

    // Injetar repository generico para acessar os dados das parcelas

    public List<ParcelaSac> calcularParcelas(SimulacaoRequestDTO dto, Produto produto);
}
