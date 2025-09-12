package com.br.domain.repository;


import com.br.domain.dto.response.SimulacaoListaResponseDTO;
import com.br.domain.dto.response.SimulacaoPorProdutoDTO;
import com.br.domain.model.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {

    public List<SimulacaoListaResponseDTO> findSimulacoesResumo(int pageIndex, int pageSize) {
        return getEntityManager().createQuery("""
    SELECT new com.br.domain.dto.response.SimulacaoListaResponseDTO(
        CONCAT(
            CAST(YEAR(s.createdAt) AS STRING),
            LPAD(CAST(MONTH(s.createdAt) AS STRING), 2, '0'),
            CAST(s.nuSimulacao AS STRING)
        ),
        s.vrSimulacao,
        s.prSimulacao,
        CAST(COALESCE(
            (SELECT SUM(ps.vrPrestacao) FROM ParcelaSac ps WHERE ps.nuSimulacao = s.nuSimulacao),
            (SELECT SUM(pp.vrPrestacao) FROM ParcelaPrice pp WHERE pp.nuSimulacao = s.nuSimulacao),
            0.0
        ) AS BigDecimal)
    )
    FROM Simulacao s
    ORDER BY s.nuSimulacao DESC
    """, SimulacaoListaResponseDTO.class)
                .setFirstResult(pageIndex * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<SimulacaoPorProdutoDTO> findSimulacoesPorDataProduto(LocalDate dataReferencia) {
        return getEntityManager().createQuery("""
    SELECT 
        p.coProduto as codigoProduto,
        p.noProduto as nomeProduto,
        CAST(AVG(p.pcTaxaJuros) AS BigDecimal) as taxaJurosMedia,
        CAST(AVG(
            CASE
                WHEN EXISTS (SELECT 1 FROM ParcelaSac ps WHERE ps.nuSimulacao = s.nuSimulacao) THEN
                    (SELECT AVG(ps2.vrPrestacao) FROM ParcelaSac ps2 WHERE ps2.nuSimulacao = s.nuSimulacao)
                ELSE
                    (SELECT AVG(pp.vrPrestacao) FROM ParcelaPrice pp WHERE pp.nuSimulacao = s.nuSimulacao)
            END
        ) AS BigDecimal) as prestacaoMedia,
        SUM(s.vrSimulacao) as somaSimulacao,
        CAST(SUM(
            CASE
                WHEN EXISTS (SELECT 1 FROM ParcelaSac ps WHERE ps.nuSimulacao = s.nuSimulacao) THEN
                    COALESCE((SELECT SUM(ps2.vrPrestacao) FROM ParcelaSac ps2 WHERE ps2.nuSimulacao = s.nuSimulacao), 0)
                ELSE
                    COALESCE((SELECT SUM(pp.vrPrestacao) FROM ParcelaPrice pp WHERE pp.nuSimulacao = s.nuSimulacao), 0)
            END
        ) AS BigDecimal) as somaPrestacao
    FROM Simulacao s
    JOIN s.produto p
    WHERE DATE(s.createdAt) = :dataReferencia
    GROUP BY p.coProduto, p.noProduto
    ORDER BY p.coProduto
    """, SimulacaoPorProdutoDTO.class)
                .setParameter("dataReferencia", dataReferencia)
                .getResultList();
    }

    public long countSimulacoes() {
        return getEntityManager().createQuery("SELECT COUNT(s) FROM Simulacao s", Long.class)
                .getSingleResult();
    }
}