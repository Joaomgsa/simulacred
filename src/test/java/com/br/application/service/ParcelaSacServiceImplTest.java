package com.br.application.service;

import com.br.domain.dto.request.SimulacaoRequestDTO;
import com.br.domain.model.ParcelaSac;
import com.br.domain.model.Produto;
import com.br.domain.repository.ParcelaSacRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class ParcelaSacServiceImplTest {

    @Inject
    ParcelaSacServiceImpl parcelaSacService;

    @InjectMock
    ParcelaSacRepository parcelaSacRepository;

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
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(simulacaoRequest, produto);

        // Then
        assertNotNull(result);
        assertEquals(12, result.size());

        // Verify first installment
        ParcelaSac primeiraParcela = result.get(0);
        assertEquals(1, primeiraParcela.getNuParcela());
        assertEquals(Long.valueOf("1"), primeiraParcela.getNuSimulacao());
        assertEquals(new BigDecimal("10000.00"), primeiraParcela.getVrSaldodevedor());
        
        // Interest should be 2.5% of 10000 = 250.00
        assertEquals(new BigDecimal("250.00"), primeiraParcela.getVrJuros());
        
        // Amortization in SAC is constant: 10000/12 = 833.33
        // Installment = amortization + interest = 833.33 + 250.00 = 1083.33
        assertEquals(new BigDecimal("1083.33"), primeiraParcela.getVrPrestacao());

        // Verify last installment
        ParcelaSac ultimaParcela = result.get(11);
        assertEquals(12, ultimaParcela.getNuParcela());
        
        // In the last installment, remaining balance should be one amortization = 833.33 (approximately)
        // Due to rounding, this might be slightly different  
        assertTrue(ultimaParcela.getVrSaldodevedor().compareTo(new BigDecimal("800.00")) > 0);
        assertTrue(ultimaParcela.getVrSaldodevedor().compareTo(new BigDecimal("900.00")) < 0);
        
        // Interest should be 2.5% of 833.33 = 20.83
        assertEquals(new BigDecimal("20.83"), ultimaParcela.getVrJuros());
        
        // Installment = 833.33 + 20.83 = 854.16
        assertEquals(new BigDecimal("854.16"), ultimaParcela.getVrPrestacao());

        verify(parcelaSacRepository).persist(any(List.class));
    }

    @Test
    void testCalcularParcelas_ConstantAmortization() {
        // Given
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(simulacaoRequest, produto);

        // Then
        BigDecimal expectedAmortization = new BigDecimal("833.33"); // 10000/12

        // Verify that amortization is constant across all installments
        for (int i = 0; i < result.size(); i++) {
            ParcelaSac parcela = result.get(i);
            BigDecimal amortization = parcela.getVrPrestacao().subtract(parcela.getVrJuros());
            assertEquals(expectedAmortization, amortization, 
                String.format("Amortization should be constant in installment %d", i + 1));
        }
    }

    @Test
    void testCalcularParcelas_DecreasingInterest() {
        // Given
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // Interest should decrease with each installment in SAC
        for (int i = 1; i < result.size(); i++) {
            ParcelaSac parcelaAnterior = result.get(i - 1);
            ParcelaSac parcelaAtual = result.get(i);
            
            assertTrue(parcelaAnterior.getVrJuros().compareTo(parcelaAtual.getVrJuros()) > 0,
                String.format("Interest should decrease: installment %d (%.2f) should be greater than installment %d (%.2f)",
                    i, parcelaAnterior.getVrJuros(), i + 1, parcelaAtual.getVrJuros()));
        }
    }

    @Test
    void testCalcularParcelas_DecreasingInstallments() {
        // Given
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(simulacaoRequest, produto);

        // Then
        // Installments should decrease with each payment in SAC
        for (int i = 1; i < result.size(); i++) {
            ParcelaSac parcelaAnterior = result.get(i - 1);
            ParcelaSac parcelaAtual = result.get(i);
            
            assertTrue(parcelaAnterior.getVrPrestacao().compareTo(parcelaAtual.getVrPrestacao()) > 0,
                String.format("Installment should decrease: installment %d (%.2f) should be greater than installment %d (%.2f)",
                    i, parcelaAnterior.getVrPrestacao(), i + 1, parcelaAtual.getVrPrestacao()));
        }
    }

    @Test
    void testCalcularParcelas_SaldoDevedorDecrease() {
        // Given
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(simulacaoRequest, produto);

        // Then
        BigDecimal expectedAmortization = new BigDecimal("833.33");
        
        for (int i = 0; i < result.size(); i++) {
            ParcelaSac parcela = result.get(i);
            BigDecimal expectedSaldo = simulacaoRequest.valorDesejado()
                .subtract(expectedAmortization.multiply(BigDecimal.valueOf(i)));
            
            assertEquals(expectedSaldo, parcela.getVrSaldodevedor(),
                String.format("Outstanding balance mismatch in installment %d", i + 1));
        }
    }

    @Test
    void testCalcularParcelas_SingleInstallment() {
        // Given
        SimulacaoRequestDTO requestUmaParcela = new SimulacaoRequestDTO(
            new BigDecimal("1000.00"),
            1,
            "2"
        );
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(requestUmaParcela, produto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        ParcelaSac parcela = result.get(0);
        assertEquals(1, parcela.getNuParcela());
        assertEquals(new BigDecimal("1000.00"), parcela.getVrSaldodevedor());
        assertEquals(new BigDecimal("25.00"), parcela.getVrJuros()); // 2.5% of 1000
        assertEquals(new BigDecimal("1025.00"), parcela.getVrPrestacao()); // 1000 + 25

        verify(parcelaSacRepository).persist(any(List.class));
    }

    @Test
    void testCalcularParcelas_SmallAmount() {
        // Given
        SimulacaoRequestDTO requestPequeno = new SimulacaoRequestDTO(
            new BigDecimal("600.00"),
            6,
            "3"
        );
        doNothing().when(parcelaSacRepository).persist(any(List.class));

        // When
        List<ParcelaSac> result = parcelaSacService.calcularParcelas(requestPequeno, produto);

        // Then
        assertNotNull(result);
        assertEquals(6, result.size());

        // Verify constant amortization of 100.00 (600/6)
        BigDecimal expectedAmortization = new BigDecimal("100.00");
        
        for (ParcelaSac parcela : result) {
            BigDecimal amortization = parcela.getVrPrestacao().subtract(parcela.getVrJuros());
            assertEquals(expectedAmortization, amortization);
        }

        verify(parcelaSacRepository).persist(any(List.class));
    }
}