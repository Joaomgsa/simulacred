package com.br.domain.repository;

import com.br.domain.model.ParcelaSac;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ParcelaSacRepository implements PanacheRepository<ParcelaSac> {

    public List<ParcelaSac> findBySimulacaoId(Long simulacaoId) {
        return find("simulacao.nuSimulacao", simulacaoId).list();
    }

    public List<ParcelaSac> findBySimulacaoIdOrderByParcela(Long simulacaoId) {
        return find("simulacao.nuSimulacao = ?1 order by numeroParcela", simulacaoId).list();
    }
}