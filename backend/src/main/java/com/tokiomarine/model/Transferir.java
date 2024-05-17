package com.tokiomarine.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transferir {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String contaOrigem;
	private String contaDestino;
	private BigDecimal valorTransferencia;
	private BigDecimal livre;
	private LocalDate dataTransferencia;
	private LocalDate agendamentoData;

	public Transferir(String contaOrigem, String contaDestino, BigDecimal valorTransferencia,
			LocalDate dataTransferencia) {
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
		this.valorTransferencia = valorTransferencia;
		this.dataTransferencia = dataTransferencia;
	}

	public Transferir() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public String getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}

	public BigDecimal getValorTransferencia() {
		return ValorTransferencia;
	}

	public void setValorTransferencia(BigDecimal valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}

	public BigDecimal getLivre() {
		return livre;
	}

	public void setLivre(BigDecimal livre) {
		this.livre = livre;
	}

	public LocalDate getDataTransferencia() {
		return dataTransferencia;
	}

	public void setDataTransferencia(LocalDate dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}

	public LocalDate getAgendamentoData() {
		return agendamentoData;
	}

	public void setAgendamentoData(LocalDate agendamentoData) {
		this.agendamentoData = agendamentoData;
	}
}
