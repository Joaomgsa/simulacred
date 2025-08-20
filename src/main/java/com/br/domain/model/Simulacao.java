package com.br.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nu_simulacao")
    private Long nuSimulacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_produto", nullable = false)
    private Produto produto;

    @Column(name = "vr_simulacao", precision = 18, scale = 2)
    private BigDecimal vrSimulacao;

    @Column(name = "pr_simulacao")
    private Integer prSimulacao;

    @Column(name = "vr_juros", precision = 18, scale = 2)
    private BigDecimal vrJuros;

    @Column(name = "vr_total", precision = 18, scale = 2)
    private BigDecimal vrTotal;

    @Column(name = "vr_parcela", precision = 18, scale = 2)
    private BigDecimal vrParcela;

    public Simulacao() {}

    public Simulacao(Produto produto, BigDecimal vrSimulacao, Integer prSimulacao) {
        this.produto = produto;
        this.vrSimulacao = vrSimulacao;
        this.prSimulacao = prSimulacao;
    }

    // Getters and Setters
    public Long getNuSimulacao() { return nuSimulacao; }
    public void setNuSimulacao(Long nuSimulacao) { this.nuSimulacao = nuSimulacao; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public BigDecimal getVrSimulacao() { return vrSimulacao; }
    public void setVrSimulacao(BigDecimal vrSimulacao) { this.vrSimulacao = vrSimulacao; }

    public Integer getPrSimulacao() { return prSimulacao; }
    public void setPrSimulacao(Integer prSimulacao) { this.prSimulacao = prSimulacao; }

    public BigDecimal getVrJuros() { return vrJuros; }
    public void setVrJuros(BigDecimal vrJuros) { this.vrJuros = vrJuros; }

    public BigDecimal getVrTotal() { return vrTotal; }
    public void setVrTotal(BigDecimal vrTotal) { this.vrTotal = vrTotal; }

    public BigDecimal getVrParcela() { return vrParcela; }
    public void setVrParcela(BigDecimal vrParcela) { this.vrParcela = vrParcela; }
}
