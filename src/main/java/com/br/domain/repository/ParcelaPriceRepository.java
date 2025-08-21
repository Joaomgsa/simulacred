package com.br.domain.repository;

import com.br.domain.model.ParcelaPrice;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ParcelaPriceRepository implements PanacheRepository<ParcelaPrice> {

    public List<ParcelaPrice> findBySimulacaoId(Long simulacaoId) {
        return find("simulacao.nuSimulacao", simulacaoId).list();
    }

    public List<ParcelaPrice> findBySimulacaoIdOrderByParcela(Long simulacaoId) {
        return find("simulacao.nuSimulacao = ?1 order by numeroParcela", simulacaoId).list();
    }
}