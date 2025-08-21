package com.br.domain.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sumarizacao_calculo")
public class SumarizacaoCalculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sumarizacao")
    private Long idSumarizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nu_simulacao", nullable = false)
    private Simulacao simulacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_calculo", nullable = false, length = 10)
    private TipoCalculo tipoCalculo;

    @Column(name = "total_amortizacao", precision = 18, scale = 2, nullable = false)
    private BigDecimal totalAmortizacao;

    @Column(name = "total_juros", precision = 18, scale = 2, nullable = false)
    private BigDecimal totalJuros;

    @Column(name = "total_prestacoes", precision = 18, scale = 2, nullable = false)
    private BigDecimal totalPrestacoes;

    @Column(name = "quantidade_parcelas", nullable = false)
    private Integer quantidadeParcelas;

    @CreationTimestamp
    @Column(name = "timestamp_sumarizacao", updatable = false)
    private LocalDateTime timestampSumarizacao;

    // Enum for calculation type
    public enum TipoCalculo {
        SAC, PRICE
    }

    // Constructors
    public SumarizacaoCalculo() {}

    public SumarizacaoCalculo(Simulacao simulacao, TipoCalculo tipoCalculo, BigDecimal totalAmortizacao,
                             BigDecimal totalJuros, BigDecimal totalPrestacoes, Integer quantidadeParcelas) {
        this.simulacao = simulacao;
        this.tipoCalculo = tipoCalculo;
        this.totalAmortizacao = totalAmortizacao;
        this.totalJuros = totalJuros;
        this.totalPrestacoes = totalPrestacoes;
        this.quantidadeParcelas = quantidadeParcelas;
    }

    // Getters and Setters
    public Long getIdSumarizacao() {
        return idSumarizacao;
    }

    public void setIdSumarizacao(Long idSumarizacao) {
        this.idSumarizacao = idSumarizacao;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public TipoCalculo getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(TipoCalculo tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public BigDecimal getTotalAmortizacao() {
        return totalAmortizacao;
    }

    public void setTotalAmortizacao(BigDecimal totalAmortizacao) {
        this.totalAmortizacao = totalAmortizacao;
    }

    public BigDecimal getTotalJuros() {
        return totalJuros;
    }

    public void setTotalJuros(BigDecimal totalJuros) {
        this.totalJuros = totalJuros;
    }

    public BigDecimal getTotalPrestacoes() {
        return totalPrestacoes;
    }

    public void setTotalPrestacoes(BigDecimal totalPrestacoes) {
        this.totalPrestacoes = totalPrestacoes;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public LocalDateTime getTimestampSumarizacao() {
        return timestampSumarizacao;
    }

    public void setTimestampSumarizacao(LocalDateTime timestampSumarizacao) {
        this.timestampSumarizacao = timestampSumarizacao;
    }
}