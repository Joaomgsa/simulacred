package com.br.domain.repository;

import com.br.domain.model.SumarizacaoCalculo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SumarizacaoCalculoRepository implements PanacheRepository<SumarizacaoCalculo> {

    public List<SumarizacaoCalculo> findBySimulacaoId(Long simulacaoId) {
        return find("simulacao.nuSimulacao", simulacaoId).list();
    }

    public List<SumarizacaoCalculo> findByTipoCalculo(SumarizacaoCalculo.TipoCalculo tipoCalculo) {
        return find("tipoCalculo", tipoCalculo).list();
    }

    public SumarizacaoCalculo findBySimulacaoIdAndTipoCalculo(Long simulacaoId, SumarizacaoCalculo.TipoCalculo tipoCalculo) {
        return find("simulacao.nuSimulacao = ?1 and tipoCalculo = ?2", simulacaoId, tipoCalculo).firstResult();
    }
}