package com.tokiomarine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tokiomarine.model.Transferir;

@Component
public class TransferirValidator {

	public void validateTransferForScheduling(Transferir transferir) {
		validateTransferInput(transferir);

		if (transferir.getOriginAccount().length() != 10 || transferir.getDestinationAccount().length() != 10) {
			throw new IllegalArgumentException(
					"Os números das contas de origem e de destino devem ter exatamente 10 dígitos.");
		}
	}

	private void validateTransferInput(Transfer transferir) {
		LocalDate dataAtual = LocalDate.now();

		if (transferir.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
		}

		if (transferir.getOriginAccount().equals(transferir.getDestinationAccount())) {
			throw new IllegalArgumentException("Os números das contas de origem e de destino não podem ser iguais.");
		}

		if (transferir.getTransferDate().isBefore(currentDate)
				|| transferir.getTransferDate().isAfter(dataAtual.plusDays(50))) {
			throw new IllegalArgumentException(
					"Data de transferência inválida. Deve ser entre hoje e 50 dias a partir de hoje.");
		}
		long diaDiferenca = ChronoUnit.DAYS.between(currentDate, transferir.getTransferDate());
		BigDecimal valorMinimoTransferencia = BigDecimal.ZERO;

		if (diaDiferenca == 0) {
			valorMinimoTransferencia = new BigDecimal("10");
		} else if (diaDiferenca >= 1 && diaDiferenca <= 10) {
			valorMinimoTransferencia = new BigDecimal("20");
		}

		if (transfer.getTransferAmount().compareTo(minTransferAmount) < 0) {
			throw new IllegalArgumentException(
					"Valor de transferência inválido. O valor mínimo para o prazo selecionado é: R$" + minTransferAmount);
		}
	}

	public Map<String, String> createSuccessResponse() {
		return Collections.singletonMap("message", "Transferência agendada com sucesso!");
	}

	public Map<String, String> createErrorResponse(String errorMessage) {
		return Collections.singletonMap("error", errorMessage);
	}
}
