package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.Produto;
import com.br.domain.repository.ParcelaPriceRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class ParcelaPriceServiceImplTest {

    @Inject
    ParcelaPriceServiceImpl parcelaPriceService;

    @InjectMock
    ParcelaPriceRepository parcelaPriceRepository;

    private SimulacaoRequestDTO simulacaoRequest;
    private Produto produto;

    @BeforeEach
    void setUp() {
        simulacaoRequest = new SimulacaoRequestDTO(
            new BigDecimal("10000.00"),
            12,
            "1"
        );

        produto = new Produto();
        produto.setCoProduto(1L);
        produto.setNoProduto("Crédito Pessoal");
        produto.setPcTaxaJuros(new BigDecimal("2.50")); // 2.5% ao mês
        produto.setNuMinimoParcelas((short) 5);
        produto.setNuMaximoParcelas((short) 60);
        produto.setVrMinimo(new BigDecimal("500.00"));
        produto.setVrMaximo(new BigDecimal("50000.00"));
    }

    @Test
    void testCalcularParcelas_Success() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        assertNotNull(result);
        assertEquals(12, result.size());

        // Verify first installment
        ParcelaPrice primeiraParcela = result.get(0);
        assertEquals(1, primeiraParcela.getNuParcela());
        assertEquals(Long.valueOf("1"), primeiraParcela.getNuSimulacao());
        assertEquals(new BigDecimal("10000.00"), primeiraParcela.getVrSaldodevedor());
        
        // Interest on first installment: 2.5% of 10000 = 250.00
        assertEquals(new BigDecimal("250.00"), primeiraParcela.getVrJuros());
        
        // In PRICE, installment amount should be constant
        // Calculate expected installment using PRICE formula
        BigDecimal txJurosMensal = new BigDecimal("0.025"); // 2.5%
        BigDecimal umMaisJuros = BigDecimal.ONE.add(txJurosMensal); // 1.025
        BigDecimal umMaisJurosPotencia = umMaisJuros.pow(12); // 1.025^12
        
        BigDecimal prestacaoEsperada = new BigDecimal("10000.00")
            .multiply(txJurosMensal)
            .multiply(umMaisJurosPotencia)
            .divide(umMaisJurosPotencia.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        
        assertEquals(prestacaoEsperada, primeiraParcela.getVrPrestacao());

        verify(parcelaPriceRepository).persist(any(List.class));
    }

    @Test
    void testCalcularParcelas_ConstantInstallments() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // In PRICE system, all installments should be equal
        BigDecimal firstInstallment = result.get(0).getVrPrestacao();
        
        for (int i = 1; i < result.size(); i++) {
            assertEquals(firstInstallment, result.get(i).getVrPrestacao(),
                String.format("All installments should be equal in PRICE system. Installment %d differs", i + 1));
        }
    }

    @Test
    void testCalcularParcelas_DecreasingInterest() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // Interest should decrease with each installment in PRICE
        for (int i = 1; i < result.size(); i++) {
            ParcelaPrice parcelaAnterior = result.get(i - 1);
            ParcelaPrice parcelaAtual = result.get(i);
            
            assertTrue(parcelaAnterior.getVrJuros().compareTo(parcelaAtual.getVrJuros()) > 0,
                String.format("Interest should decrease: installment %d (%.2f) should be greater than installment %d (%.2f)",
                    i, parcelaAnterior.getVrJuros(), i + 1, parcelaAtual.getVrJuros()));
        }
    }

    @Test
    void testCalcularParcelas_IncreasingAmortization() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // Amortization should increase with each installment in PRICE
        for (int i = 1; i < result.size(); i++) {
            ParcelaPrice parcelaAnterior = result.get(i - 1);
            ParcelaPrice parcelaAtual = result.get(i);
            
            BigDecimal amortizacaoAnterior = parcelaAnterior.getVrPrestacao().subtract(parcelaAnterior.getVrJuros());
            BigDecimal amortizacaoAtual = parcelaAtual.getVrPrestacao().subtract(parcelaAtual.getVrJuros());
            
            assertTrue(amortizacaoAtual.compareTo(amortizacaoAnterior) > 0,
                String.format("Amortization should increase: installment %d (%.2f) should be less than installment %d (%.2f)",
                    i, amortizacaoAnterior, i + 1, amortizacaoAtual));
        }
    }

    @Test
    void testCalcularParcelas_SaldoDevedorDecrease() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // Outstanding balance should decrease with each installment
        for (int i = 1; i < result.size(); i++) {
            ParcelaPrice parcelaAnterior = result.get(i - 1);
            ParcelaPrice parcelaAtual = result.get(i);
            
            assertTrue(parcelaAnterior.getVrSaldodevedor().compareTo(parcelaAtual.getVrSaldodevedor()) > 0,
                String.format("Outstanding balance should decrease: installment %d (%.2f) should be greater than installment %d (%.2f)",
                    i, parcelaAnterior.getVrSaldodevedor(), i + 1, parcelaAtual.getVrSaldodevedor()));
        }
    }

    @Test
    void testCalcularParcelas_LastInstallmentZeroBalance() {
        // Given
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // After calculating all installments, the remaining balance should be close to zero
        ParcelaPrice ultimaParcela = result.get(result.size() - 1);
        BigDecimal amortizacaoUltima = ultimaParcela.getVrPrestacao().subtract(ultimaParcela.getVrJuros());
        BigDecimal saldoFinal = ultimaParcela.getVrSaldodevedor().subtract(amortizacaoUltima);
        
        // Should be zero or very close to zero (due to rounding)
        assertTrue(saldoFinal.abs().compareTo(new BigDecimal("1.00")) <= 0,
            String.format("Final balance should be zero or very close to zero, but was: %.2f", saldoFinal));
    }

    @Test
    void testCalcularParcelas_SingleInstallment() {
        // Given
        SimulacaoRequestDTO requestUmaParcela = new SimulacaoRequestDTO(
            new BigDecimal("1000.00"),
            1,
            "2"
        );
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(requestUmaParcela, produto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        ParcelaPrice parcela = result.get(0);
        assertEquals(1, parcela.getNuParcela());
        assertEquals(new BigDecimal("1000.00"), parcela.getVrSaldodevedor());
        assertEquals(new BigDecimal("25.00"), parcela.getVrJuros()); // 2.5% of 1000
        assertEquals(new BigDecimal("1025.00"), parcela.getVrPrestacao()); // 1000 + 25

        verify(parcelaPriceRepository).persist(any(List.class));
    }

    @Test
    void testCalcularParcelas_SmallAmount() {
        // Given
        SimulacaoRequestDTO requestPequeno = new SimulacaoRequestDTO(
            new BigDecimal("600.00"),
            6,
            "3"
        );
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(requestPequeno, produto);

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());

        // Verify all installments are equal (PRICE characteristic)
        BigDecimal firstInstallment = result.get(0).getVrPrestacao();
        for (ParcelaPrice parcela : result) {
            assertEquals(firstInstallment, parcela.getVrPrestacao());
        }

        verify(parcelaPriceRepository).persist(any(List.class));
    }

    @Test
    void testCalcularParcelas_HighInterestRate() {
        // Given
        Produto produtoAltoJuros = new Produto();
        produtoAltoJuros.setPcTaxaJuros(new BigDecimal("10.00")); // 10% ao mês
        
        doNothing().when(parcelaPriceRepository).persist(any(List.class));

        // When
        List<ParcelaPrice> result = parcelaPriceService.calcularParcelas(simulacaoRequest, produtoAltoJuros);

        // Then
        assertNotNull(result);
        assertEquals(12, result.size());

        ParcelaPrice primeiraParcela = result.get(0);
        // Interest should be 10% of 10000 = 1000.00
        assertEquals(new BigDecimal("1000.00"), primeiraParcela.getVrJuros());
        
        // Installment should be higher due to higher interest rate
        assertTrue(primeiraParcela.getVrPrestacao().compareTo(new BigDecimal("1000.00")) > 0);

        verify(parcelaPriceRepository).persist(any(List.class));
    }
}