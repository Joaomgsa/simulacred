package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.ParcelaDTO;
import com.br.domain.dto.response.ProdutoDTO;
import com.br.domain.dto.response.ResultadoSimulacaoDTO;
import com.br.domain.dto.response.SimulacaoResponseDTO;
import com.br.domain.model.Produto;
import com.br.domain.model.Simulacao;
import com.br.domain.repository.SimulacaoRepository;
import com.br.exception.NegocioException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SimulacaoServiceImpl implements SimulacaoService {

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    ParcelaService parcelaService;

    @Inject
    ProdutoService produtoService;


    @Override
    public void validarSimulacao(SimulacaoRequestDTO simulacaoRequestDTO){
            //
    };

    @Override
    @Transactional
    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequest) {
        Produto prod = produtoService.findByPrazoAndValor(
                simulacaoRequest.prazo(),
                simulacaoRequest.valorDesejado()
        );

        NegocioException.throwIf(prod == null, "Produto não encontrado para o prazo e valor informados.");

        parcelaService.calcularParcelas(simulacaoRequest, prod);

        // registrar a simulação no banco de dados
        persistirSimulacao(simulacaoRequest, prod);


        // chamar metodo de calculo parcelas SAC

        // chamar metodo de calculo parcelas PRICE

        SimulacaoResponseDTO mockResponse = criarMockResponse();

        System.out.println("Simulação realizada com sucesso: " + mockResponse);
        return mockResponse;
    }


    private void persistirSimulacao(SimulacaoRequestDTO request, Produto produto) {
        Simulacao simulacao = new Simulacao();
        simulacao.setVrSimulacao(request.valorDesejado());
        simulacao.setPrSimulacao(request.prazo());
        simulacao.setProduto(produto);
        simulacao.setCreatedAt(LocalDateTime.now());

        simulacaoRepository.persist(simulacao);
    }

    //vai retornar as parcelas do tipo SAC
    @Override
    public void CalcularParcelaSac() {
        // Implementação da lógica para calcular a parcela SAC
    }

    // vai retornar as parcelas do tipo PRICE
    @Override
    public void CalcularParcelaPrice() {
        // Implementação da lógica para calcular a parcela SAC
    }

    @Override
    public void SumarizarValores() {
        // Implementação da lógica para calcular a parcela SAC
    }

    private SimulacaoResponseDTO criarMockResponse() {
        // Parcelas SAC
        List<ParcelaDTO> parcelasSAC = Arrays.asList(
                new ParcelaDTO(1, new BigDecimal("180.00"), new BigDecimal("16.11"), new BigDecimal("196.11")),
                new ParcelaDTO(2, new BigDecimal("180.00"), new BigDecimal("12.89"), new BigDecimal("192.89")),
                new ParcelaDTO(3, new BigDecimal("180.00"), new BigDecimal("9.67"), new BigDecimal("189.67")),
                new ParcelaDTO(4, new BigDecimal("180.00"), new BigDecimal("6.44"), new BigDecimal("186.44")),
                new ParcelaDTO(5, new BigDecimal("180.00"), new BigDecimal("3.22"), new BigDecimal("183.22"))
        );

        // Parcelas PRICE
        List<ParcelaDTO> parcelasPRICE = Arrays.asList(
                new ParcelaDTO(1, new BigDecimal("173.67"), new BigDecimal("16.11"), new BigDecimal("189.78")),
                new ParcelaDTO(2, new BigDecimal("176.78"), new BigDecimal("13.00"), new BigDecimal("189.78")),
                new ParcelaDTO(3, new BigDecimal("179.94"), new BigDecimal("9.84"), new BigDecimal("189.78")),
                new ParcelaDTO(4, new BigDecimal("183.16"), new BigDecimal("6.62"), new BigDecimal("189.78")),
                new ParcelaDTO(5, new BigDecimal("186.44"), new BigDecimal("3.34"), new BigDecimal("189.78"))
        );

        // Resultados de simulação
        List<ResultadoSimulacaoDTO> resultados = Arrays.asList(
                new ResultadoSimulacaoDTO("SAC", parcelasSAC),
                new ResultadoSimulacaoDTO("PRICE", parcelasPRICE)
        );

        return new SimulacaoResponseDTO(
                20180702L,
                1,
                "Produto 1",
                new BigDecimal("0.0179"),
                resultados
        );
    }
}
