package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.dto.response.SimulacaoResponseDTO;
import com.br.domain.model.Produto;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.SumarizacaoCalculo;
import com.br.domain.repository.ProdutoRepository;
import com.br.domain.repository.SimulacaoRepository;
import com.br.domain.repository.ParcelaSacRepository;
import com.br.domain.repository.ParcelaPriceRepository;
import com.br.domain.repository.SumarizacaoCalculoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SimulacaoServiceImpl implements SimulacaoService {

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    ParcelaSacRepository parcelaSacRepository;

    @Inject
    ParcelaPriceRepository parcelaPriceRepository;

    @Inject
    SumarizacaoCalculoRepository sumarizacaoCalculoRepository;


    @Override
    public void validarSimulacao(SimulacaoRequestDTO simulacaoRequestDTO){
            //
    };

    @Override
    @Transactional
    public SimulacaoResponseDTO simularCredito(SimulacaoRequestDTO simulacaoRequestDTO) {
        // buscar o produto pelo prazo
        Optional<Produto> prod = produtoRepository.findByPrazoAndValor(
                simulacaoRequestDTO.prazo(),
                simulacaoRequestDTO.valorDesejado()
        );

        // registrar a simulação no banco de dados

        // chamar metodo de calculo parcelas SAC

        // chamar metodo de calculo parcelas PRICE
        return null;
    }


    //vai retornar as parcelas do tipo SAC
    @Override
    public List<ParcelaSac> CalcularParcelaSac() {
        // Implementação da lógica para calcular a parcela SAC
        return null;
    }

    // vai retornar as parcelas do tipo PRICE
    @Override
    public List<ParcelaPrice> CalcularParcelaPrice() {
        // Implementação da lógica para calcular a parcela PRICE
        return null;
    }

    @Override
    public SumarizacaoCalculo SumarizarValores() {
        // Implementação da lógica para sumarizar valores
        return null;
    }
}
