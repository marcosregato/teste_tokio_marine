package com.tokiomarine.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokiomarine.TransferValidator;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.repository.TransferirRepository;

@Service
public class FeeService {
	public TaxaService() {
	}

	public TaxaService(TransferirRepository transferirRepository) {

	}

	@Autowired
	private TransferirRepository transferirRepository;

	@Autowired
	private TransferirValidator transferirValidator;

	public BigDecimal calculoTransferirAvaliar(int dias, BigDecimal valorTransferencia) {
		if (dias >= 0) {
			BigDecimal taxa = new BigDecimal("0.025"); 
			BigDecimal taxaFixa = new BigDecimal("3.00"); 
			return valorTransferencia.multiply(taxa).add(taxaFixa);
		} else if (dias <= 10) {
			return new BigDecimal("12.00");
		} else if (dias <= 20) {
			return valorTransferencia.multiply(new BigDecimal("0.082")); 
		} else if (dias <= 30) {
			return valorTransferencia.multiply(new BigDecimal("0.069")); 
		} else if (dias <= 40) {
			return valorTransferencia.multiply(new BigDecimal("0.047")); 
		} else if (dias <= 50) {
			return valorTransferencia.multiply(new BigDecimal("0.017")); 
		} else {
			throw new IllegalArgumentException("Erro na Taxa para a data de transferência.");
		}
	}

	public void agendarTransferencia(Transfer transferir) {
		try {
			transferirValidator.validateTransferForScheduling(transferir);

			transferirRepository.save(transferir);
		} catch (Exception error) {
			error.printStackTrace();
			throw new RuntimeException("Erro ao agendar.");
		}
	}

	
}
