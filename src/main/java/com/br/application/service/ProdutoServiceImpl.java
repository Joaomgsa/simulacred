package com.br.application.service;

import com.br.domain.dto.response.ProdutoDTO;
import com.br.domain.model.Produto;
import com.br.domain.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;


    @Override
    public List<ProdutoDTO> getProdutos() {
         return mapearParaDTO(produtoRepository.listAll());
    }

    // mapear objeto entity para dto
    public List<ProdutoDTO> mapearParaDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> new ProdutoDTO(
                        produto.getCoProduto().longValue(),
                        produto.getNoProduto(),
                        produto.getPcTaxaJuros(),
                        produto.getNuMinimoParcelas(),
                        produto.getNuMaximoParcelas(),
                        produto.getVrMinimo(),
                        produto.getVrMaximo()))
                .toList();
    }

}
