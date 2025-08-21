package com.br.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @Column(name = "co_produto")
    private Long coProduto;

    @Column(name = "no_produto", nullable = false, length = 200)
    private String noProduto;

    @Column(name = "pc_taxa_juros", nullable = false, precision = 10, scale = 9)
    private BigDecimal pcTaxaJuros;

    @Column(name = "nu_minimo_meses", nullable = false)
    private Short nuMinimoParcelas;

    @Column(name = "nu_maximo_meses")
    private Short nuMaximoParcelas;

    @Column(name = "vr_minimo", nullable = false, precision = 18, scale = 2)
    private BigDecimal vrMinimo;

    @Column(name = "vr_maximo", nullable = false, precision = 18, scale = 2)
    private BigDecimal vrMaximo;

    public Long getCoProduto() {
        return coProduto;
    }

    public void setCoProduto(Long coProduto) {
        this.coProduto = coProduto;
    }

    public String getNoProduto() {
        return noProduto;
    }

    public void setNoProduto(String noProduto) {
        this.noProduto = noProduto;
    }

    public BigDecimal getPcTaxaJuros() {
        return pcTaxaJuros;
    }

    public void setPcTaxaJuros(BigDecimal pcTaxaJuros) {
        this.pcTaxaJuros = pcTaxaJuros;
    }

    public Short getNuMinimoParcelas() {
        return nuMinimoParcelas;
    }

    public void setNuMinimoParcelas(Short nuMinimoParcelas) {
        this.nuMinimoParcelas = nuMinimoParcelas;
    }

    public Short getNuMaximoParcelas() {
        return nuMaximoParcelas;
    }

    public void setNuMaximoParcelas(Short nuMaximoParcelas) {
        this.nuMaximoParcelas = nuMaximoParcelas;
    }

    public BigDecimal getVrMinimo() {
        return vrMinimo;
    }

    public void setVrMinimo(BigDecimal vrMinimo) {
        this.vrMinimo = vrMinimo;
    }

    public BigDecimal getVrMaximo() {
        return vrMaximo;
    }

    public void setVrMaximo(BigDecimal vrMaximo) {
        this.vrMaximo = vrMaximo;
    }
}