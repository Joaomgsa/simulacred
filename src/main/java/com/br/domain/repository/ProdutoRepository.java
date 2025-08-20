package com.br.domain.repository;

import com.br.domain.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public Produto findByPrazo(Integer prazo) {
        return find("select p from Produto p where p.nuMinimoParcelas <= ?1 and p.nuMaximoParcelas >= ?1", prazo)
                .firstResult();
    }
}