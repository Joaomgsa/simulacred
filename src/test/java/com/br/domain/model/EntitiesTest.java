package com.br.domain.model;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class EntitiesTest {

    @Inject
    EntityManager entityManager;

    @Test
    @Transactional
    public void testParcelaSacEntity() {
        // Create and persist a Produto
        Produto produto = new Produto();
        produto.setCoProduto(1L);
        produto.setNoProduto("Produto Teste");
        produto.setPcTaxaJuros(new BigDecimal("0.01"));
        produto.setNuMinimoParcelas((short) 1);
        produto.setNuMaximoParcelas((short) 60);
        produto.setVrMinimo(new BigDecimal("100.00"));
        produto.setVrMaximo(new BigDecimal("100000.00"));
        entityManager.persist(produto);

        // Create and persist a Simulacao
        Simulacao simulacao = new Simulacao();
        simulacao.setProduto(produto);
        simulacao.setVrSimulacao(new BigDecimal("1000.00"));
        simulacao.setPrSimulacao(12);
        simulacao.setVrJuros(new BigDecimal("120.00"));
        simulacao.setVrTotal(new BigDecimal("1120.00"));
        simulacao.setVrParcela(new BigDecimal("93.33"));
        entityManager.persist(simulacao);

        // Create and persist a ParcelaSac
        ParcelaSac parcelaSac = new ParcelaSac();
        parcelaSac.setSimulacao(simulacao);
        parcelaSac.setNumeroParcela(1);
        parcelaSac.setValorAmortizacao(new BigDecimal("83.33"));
        parcelaSac.setValorJuros(new BigDecimal("10.00"));
        parcelaSac.setValorPrestacao(new BigDecimal("93.33"));
        parcelaSac.setSaldoDevedor(new BigDecimal("916.67"));
        entityManager.persist(parcelaSac);

        entityManager.flush();

        // Verify the entity was persisted correctly
        assertNotNull(parcelaSac.getIdParcelaSac());
        assertEquals(1, parcelaSac.getNumeroParcela());
        assertEquals(new BigDecimal("83.33"), parcelaSac.getValorAmortizacao());
        assertNotNull(parcelaSac.getCreatedAt());
    }

    @Test
    @Transactional
    public void testParcelaPriceEntity() {
        // Create and persist a Produto
        Produto produto = new Produto();
        produto.setCoProduto(2L);
        produto.setNoProduto("Produto Teste 2");
        produto.setPcTaxaJuros(new BigDecimal("0.01"));
        produto.setNuMinimoParcelas((short) 1);
        produto.setNuMaximoParcelas((short) 60);
        produto.setVrMinimo(new BigDecimal("100.00"));
        produto.setVrMaximo(new BigDecimal("100000.00"));
        entityManager.persist(produto);

        // Create and persist a Simulacao
        Simulacao simulacao = new Simulacao();
        simulacao.setProduto(produto);
        simulacao.setVrSimulacao(new BigDecimal("1000.00"));
        simulacao.setPrSimulacao(12);
        simulacao.setVrJuros(new BigDecimal("120.00"));
        simulacao.setVrTotal(new BigDecimal("1120.00"));
        simulacao.setVrParcela(new BigDecimal("93.33"));
        entityManager.persist(simulacao);

        // Create and persist a ParcelaPrice
        ParcelaPrice parcelaPrice = new ParcelaPrice();
        parcelaPrice.setSimulacao(simulacao);
        parcelaPrice.setNumeroParcela(1);
        parcelaPrice.setValorAmortizacao(new BigDecimal("83.33"));
        parcelaPrice.setValorJuros(new BigDecimal("10.00"));
        parcelaPrice.setValorPrestacao(new BigDecimal("93.33"));
        parcelaPrice.setSaldoDevedor(new BigDecimal("916.67"));
        entityManager.persist(parcelaPrice);

        entityManager.flush();

        // Verify the entity was persisted correctly
        assertNotNull(parcelaPrice.getIdParcelaPrice());
        assertEquals(1, parcelaPrice.getNumeroParcela());
        assertEquals(new BigDecimal("83.33"), parcelaPrice.getValorAmortizacao());
        assertNotNull(parcelaPrice.getCreatedAt());
    }

    @Test
    @Transactional
    public void testSumarizacaoCalculoEntity() {
        // Create and persist a Produto
        Produto produto = new Produto();
        produto.setCoProduto(3L);
        produto.setNoProduto("Produto Teste 3");
        produto.setPcTaxaJuros(new BigDecimal("0.01"));
        produto.setNuMinimoParcelas((short) 1);
        produto.setNuMaximoParcelas((short) 60);
        produto.setVrMinimo(new BigDecimal("100.00"));
        produto.setVrMaximo(new BigDecimal("100000.00"));
        entityManager.persist(produto);

        // Create and persist a Simulacao
        Simulacao simulacao = new Simulacao();
        simulacao.setProduto(produto);
        simulacao.setVrSimulacao(new BigDecimal("1000.00"));
        simulacao.setPrSimulacao(12);
        simulacao.setVrJuros(new BigDecimal("120.00"));
        simulacao.setVrTotal(new BigDecimal("1120.00"));
        simulacao.setVrParcela(new BigDecimal("93.33"));
        entityManager.persist(simulacao);

        // Create and persist a SumarizacaoCalculo
        SumarizacaoCalculo sumarizacao = new SumarizacaoCalculo();
        sumarizacao.setSimulacao(simulacao);
        sumarizacao.setTipoCalculo(SumarizacaoCalculo.TipoCalculo.SAC);
        sumarizacao.setTotalAmortizacao(new BigDecimal("1000.00"));
        sumarizacao.setTotalJuros(new BigDecimal("120.00"));
        sumarizacao.setTotalPrestacoes(new BigDecimal("1120.00"));
        sumarizacao.setQuantidadeParcelas(12);
        entityManager.persist(sumarizacao);

        entityManager.flush();

        // Verify the entity was persisted correctly
        assertNotNull(sumarizacao.getIdSumarizacao());
        assertEquals(SumarizacaoCalculo.TipoCalculo.SAC, sumarizacao.getTipoCalculo());
        assertEquals(new BigDecimal("1000.00"), sumarizacao.getTotalAmortizacao());
        assertEquals(12, sumarizacao.getQuantidadeParcelas());
        assertNotNull(sumarizacao.getTimestampSumarizacao());
    }
}