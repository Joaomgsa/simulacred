package com.br.application.service;

import com.br.domain.dto.response.ProdutoDTO;
import com.br.domain.model.Produto;
import com.br.domain.repository.ProdutoRepository;
import com.br.exception.GeneralException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;


    @Override
    public Produto findByPrazoAndValor(Integer prazo, BigDecimal valor) {
        try {
            return produtoRepository.findByPrazoAndValor(prazo, valor)
                    .orElseThrow(() -> new GeneralException(
                            String.format("Nenhum produto encontrado para prazo de %d meses e valor de R$ %.2f",
                                    prazo, valor)
                    ));
        } catch (Exception e) {
            if (e instanceof GeneralException) {
                throw e;
            }
            throw new GeneralException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    @Override
    public List<ProdutoDTO> getProdutos() {
        List<Produto> produtos = produtoRepository.listAll();
        return mapearParaDTO(produtos);
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

    @Override
    public ProdutoDTO filtrarProduto(Integer prazo, BigDecimal valor) {
        return produtoRepository.findByPrazoAndValor(prazo, valor)
                .map(this::mapearParaDTO)
                .orElseThrow(() -> new GeneralException(
                        String.format("Nenhum produto encontrado para prazo de %d meses e valor de R$ %.2f",
                                prazo, valor)
                ));
    }


    // mapear RETORNO DA QUERY PARA DTO
    public ProdutoDTO mapearParaDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getCoProduto().longValue(),
                produto.getNoProduto(),
                produto.getPcTaxaJuros(),
                produto.getNuMinimoParcelas(),
                produto.getNuMaximoParcelas(),
                produto.getVrMinimo(),
                produto.getVrMaximo()
        );
    }

}
