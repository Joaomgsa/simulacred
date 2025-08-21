package com.br.application.service;

import com.br.domain.dto.response.ProdutoDTO;
import com.br.domain.model.Produto;

import java.math.BigDecimal;
import java.util.List;


public interface ProdutoService {

    public List<ProdutoDTO> getProdutos();

    public Produto findByPrazoAndValor(Integer prazo, BigDecimal valor);

    public ProdutoDTO filtrarProduto(Integer prazo, BigDecimal valor);
}
