package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.SimulacaoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;


public interface SimulacaoService {

    public void validarSimulacao(SimulacaoRequestDTO simulacaoRequestDTO);
    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequestDTO);
    public void CalcularParcelaSac();
    public void CalcularParcelaPrice();
    public void SumarizarValores();
}
