package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.Produto;

import java.util.List;

public interface ParcelaPriceService {

    // Injetar repository generico para acessar os dados das parcelas

    public List<ParcelaPrice> calcularParcelas(SimulacaoRequestDTO dto, Produto produto);
}
