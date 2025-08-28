package com.br.domain.repository;

import com.br.domain.model.ParcelaPrice;
import com.br.domain.model.ParcelaSac;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParcelaPriceRepository implements PanacheRepository<ParcelaPrice> {
}
