package com.br.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcela_sac")
public class ParcelaSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela_sac")
    private Long idParcelaSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nu_simulacao", nullable = false)
    private Simulacao simulacao;

    @Column(name = "nu_parcela", nullable = false)
    private Integer numeroParcela;

    @Column(name = "vr_amortizacao", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorAmortizacao;

    @Column(name = "vr_juros", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorJuros;

    @Column(name = "vr_prestacao", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorPrestacao;

    @Column(name = "vr_saldo_devedor", precision = 18, scale = 2, nullable = false)
    private BigDecimal saldoDevedor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public ParcelaSac() {}

    public ParcelaSac(Simulacao simulacao, Integer numeroParcela, BigDecimal valorAmortizacao, 
                      BigDecimal valorJuros, BigDecimal valorPrestacao, BigDecimal saldoDevedor) {
        this.simulacao = simulacao;
        this.numeroParcela = numeroParcela;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
        this.saldoDevedor = saldoDevedor;
    }

    // Getters and Setters
    public Long getIdParcelaSac() {
        return idParcelaSac;
    }

    public void setIdParcelaSac(Long idParcelaSac) {
        this.idParcelaSac = idParcelaSac;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getValorAmortizacao() {
        return valorAmortizacao;
    }

    public void setValorAmortizacao(BigDecimal valorAmortizacao) {
        this.valorAmortizacao = valorAmortizacao;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorPrestacao() {
        return valorPrestacao;
    }

    public void setValorPrestacao(BigDecimal valorPrestacao) {
        this.valorPrestacao = valorPrestacao;
    }

    public BigDecimal getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(BigDecimal saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}