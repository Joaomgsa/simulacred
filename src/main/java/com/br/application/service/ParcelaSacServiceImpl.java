package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.Produto;
import com.br.domain.repository.ParcelaSacRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ParcelaSacServiceImpl implements ParcelaSacService{

    @Inject
    ParcelaSacRepository parcelaSacRepository;

    @Override
    public List<ParcelaSac> calcularParcelas(SimulacaoRequestDTO dto, Produto produto) {

        List<ParcelaSac> parcelasSac = new ArrayList<>();

        BigDecimal valorFinanciado = dto.valorDesejado();
        BigDecimal txJuros = produto.getPcTaxaJuros().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        int numeroParcelas = dto.prazo();

        // Amortização constante no SAC
        BigDecimal amortizacao = valorFinanciado.divide(
                BigDecimal.valueOf(numeroParcelas),
                2, RoundingMode.HALF_UP
        );

        BigDecimal saldoDevedor = valorFinanciado;

        for (int i = 1; i <= numeroParcelas; i++) { // Começar de 1, não 0

            // Juros calculados sobre o saldo devedor ATUAL
            BigDecimal juros = saldoDevedor.multiply(txJuros).setScale(2, RoundingMode.HALF_UP);
            BigDecimal prestacao = amortizacao.add(juros);

            // Criar parcela com saldo devedor ANTES da amortização
            ParcelaSac parcelaSac = new ParcelaSac(
                    Long.valueOf(dto.idSimulacao()),
                    i, // Número da parcela de 1 a N
                    saldoDevedor, // Saldo devedor no início do período
                    juros,
                    prestacao);

            parcelasSac.add(parcelaSac);

            // Reduzir saldo devedor APÓS criar a parcela
            saldoDevedor = saldoDevedor.subtract(amortizacao);

            // Garantir que não fique negativo na última parcela
            if (saldoDevedor.compareTo(BigDecimal.ZERO) < 0) {
                saldoDevedor = BigDecimal.ZERO;
            }
        }

        parcelaSacRepository.persist(parcelasSac);
        return parcelasSac;
    }
}
