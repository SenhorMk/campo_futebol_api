package com.futebol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pagamento")
    private Integer codPagamento;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 2000)
    @Column(name = "ano", nullable = false)
    private Short ano;

    @NotNull(message = "Mês é obrigatório")
    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "mes", nullable = false)
    private Byte mes;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_jogador", nullable = false)
    private Jogador jogador;

    public Pagamento() {}

    public Integer getCodPagamento() { return codPagamento; }
    public void setCodPagamento(Integer codPagamento) { this.codPagamento = codPagamento; }

    public Short getAno() { return ano; }
    public void setAno(Short ano) { this.ano = ano; }

    public Byte getMes() { return mes; }
    public void setMes(Byte mes) { this.mes = mes; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Jogador getJogador() { return jogador; }
    public void setJogador(Jogador jogador) { this.jogador = jogador; }
}
