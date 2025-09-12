package com.br.application.service;

import com.br.domain.dto.response.ProdutoDTO;
import com.br.domain.model.Produto;
import com.br.domain.repository.ProdutoRepository;
import com.br.exception.GeneralException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProdutoServiceImplTest {

    @Inject
    ProdutoServiceImpl produtoService;

    @InjectMock
    ProdutoRepository produtoRepository;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setCoProduto(1L);
        produto.setNoProduto("Crédito Pessoal");
        produto.setPcTaxaJuros(new BigDecimal("2.50"));
        produto.setNuMinimoParcelas((short) 5);
        produto.setNuMaximoParcelas((short) 60);
        produto.setVrMinimo(new BigDecimal("500.00"));
        produto.setVrMaximo(new BigDecimal("50000.00"));

        produtoDTO = new ProdutoDTO(
            1L,
            "Crédito Pessoal", 
            new BigDecimal("2.50"),
            (short) 5,
            (short) 60,
            new BigDecimal("500.00"),
            new BigDecimal("50000.00")
        );
    }

    @Test
    void testFindByPrazoAndValor_Success() {
        // Given
        Integer prazo = 12;
        BigDecimal valor = new BigDecimal("10000.00");
        when(produtoRepository.findByPrazoAndValor(prazo, valor)).thenReturn(Optional.of(produto));

        // When
        Produto result = produtoService.findByPrazoAndValor(prazo, valor);

        // Then
        assertNotNull(result);
        assertEquals(produto.getCoProduto(), result.getCoProduto());
        assertEquals(produto.getNoProduto(), result.getNoProduto());
        verify(produtoRepository).findByPrazoAndValor(prazo, valor);
    }

    @Test
    void testFindByPrazoAndValor_NotFound() {
        // Given
        Integer prazo = 12;
        BigDecimal valor = new BigDecimal("10000.00");
        when(produtoRepository.findByPrazoAndValor(prazo, valor)).thenReturn(Optional.empty());

        // When & Then
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            produtoService.findByPrazoAndValor(prazo, valor);
        });

        assertTrue(exception.getMessage().contains("Nenhum produto encontrado"));
        assertTrue(exception.getMessage().contains("12 meses"));
        assertTrue(exception.getMessage().contains("R$ 10000.00"));
        verify(produtoRepository).findByPrazoAndValor(prazo, valor);
    }

    @Test
    void testFindByPrazoAndValor_RepositoryException() {
        // Given
        Integer prazo = 12;
        BigDecimal valor = new BigDecimal("10000.00");
        when(produtoRepository.findByPrazoAndValor(prazo, valor))
            .thenThrow(new RuntimeException("Database error"));

        // When & Then
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            produtoService.findByPrazoAndValor(prazo, valor);
        });

        assertTrue(exception.getMessage().contains("Erro ao buscar produto"));
        assertTrue(exception.getMessage().contains("Database error"));
        verify(produtoRepository).findByPrazoAndValor(prazo, valor);
    }

    @Test
    void testGetProdutos_Success() {
        // Given
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.listAll()).thenReturn(produtos);

        // When
        List<ProdutoDTO> result = produtoService.getProdutos();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        ProdutoDTO resultDTO = result.get(0);
        assertEquals(produto.getCoProduto(), resultDTO.coProduto());
        assertEquals(produto.getNoProduto(), resultDTO.noProduto());
        assertEquals(produto.getPcTaxaJuros(), resultDTO.pcTaxaJuros());
        verify(produtoRepository).listAll();
    }

    @Test
    void testGetProdutos_EmptyList() {
        // Given
        when(produtoRepository.listAll()).thenReturn(Arrays.asList());

        // When
        List<ProdutoDTO> result = produtoService.getProdutos();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(produtoRepository).listAll();
    }

    @Test
    void testFiltrarProduto_Success() {
        // Given
        Integer prazo = 12;
        BigDecimal valor = new BigDecimal("10000.00");
        when(produtoRepository.findByPrazoAndValor(prazo, valor)).thenReturn(Optional.of(produto));

        // When
        ProdutoDTO result = produtoService.filtrarProduto(prazo, valor);

        // Then
        assertNotNull(result);
        assertEquals(produto.getCoProduto(), result.coProduto());
        assertEquals(produto.getNoProduto(), result.noProduto());
        assertEquals(produto.getPcTaxaJuros(), result.pcTaxaJuros());
        verify(produtoRepository).findByPrazoAndValor(prazo, valor);
    }

    @Test
    void testFiltrarProduto_NotFound() {
        // Given
        Integer prazo = 12;
        BigDecimal valor = new BigDecimal("10000.00");
        when(produtoRepository.findByPrazoAndValor(prazo, valor)).thenReturn(Optional.empty());

        // When & Then
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            produtoService.filtrarProduto(prazo, valor);
        });

        assertTrue(exception.getMessage().contains("Nenhum produto encontrado"));
        verify(produtoRepository).findByPrazoAndValor(prazo, valor);
    }

    @Test
    void testMapearParaDTO_SingleProduct() {
        // When
        ProdutoDTO result = produtoService.mapearParaDTO(produto);

        // Then
        assertNotNull(result);
        assertEquals(produto.getCoProduto(), result.coProduto());
        assertEquals(produto.getNoProduto(), result.noProduto());
        assertEquals(produto.getPcTaxaJuros(), result.pcTaxaJuros());
        assertEquals(produto.getNuMinimoParcelas(), result.nuMinimoParcelas());
        assertEquals(produto.getNuMaximoParcelas(), result.nuMaximoParcelas());
        assertEquals(produto.getVrMinimo(), result.vrMinimo());
        assertEquals(produto.getVrMaximo(), result.vrMaximo());
    }

    @Test
    void testMapearParaDTO_ProductList() {
        // Given
        List<Produto> produtos = Arrays.asList(produto);

        // When
        List<ProdutoDTO> result = produtoService.mapearParaDTO(produtos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        ProdutoDTO resultDTO = result.get(0);
        assertEquals(produto.getCoProduto(), resultDTO.coProduto());
        assertEquals(produto.getNoProduto(), resultDTO.noProduto());
    }
}