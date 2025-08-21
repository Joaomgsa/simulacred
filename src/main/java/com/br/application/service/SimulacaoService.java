package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.SimulacaoResponseDTO;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.SumarizacaoCalculo;

import java.util.List;

public interface SimulacaoService {

    public void validarSimulacao(SimulacaoRequestDTO simulacaoRequestDTO);
    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequestDTO);
    public List<ParcelaSac> CalcularParcelaSac();
    public List<ParcelaPrice> CalcularParcelaPrice();
    public SumarizacaoCalculo SumarizarValores();
}
