package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.PaginatedResponseDTO;
import com.br.domain.dto.response.RelatorioSimulacaoResponseDTO;
import com.br.domain.dto.response.SimulacaoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;


public interface SimulacaoService {


    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequestDTO);
    public PaginatedResponseDTO listarSimulacoes(int pagina, int tamanho);
    public RelatorioSimulacaoResponseDTO obterRelatorioSimulacoes(LocalDate dataReferencia);

}
