package com.tokiomarine.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokiomarine.TransferValidator;
import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.service.FeeService;

@RestController
@RequestMapping("/transferir")
public class TransferirController {

	@Autowired
	private FeeService feeService;

	@Autowired
	private TransferRepository transferirRepository;

	@Autowired
	private TransferValidator transferirValidator;

	@PostMapping("/schedule")
	public ResponseEntity<Map<String, String>> scheduleTransfer(@RequestBody Transfer transferir) {
		try {
			transferValidator.validateTransferForScheduling(transfer);

			transferir.setSchedulingDate(LocalDate.now());
			long diaDiferenca = ChronoUnit.DAYS.between(transferir.getSchedulingDate(), transferir.getTransferDate());

			BigDecimal taxa = feeService.calculateTransferRate((int) diaDiferenca, transferir.getTransferAmount());
			transferir.setFee(taxa);

			feeService.scheduleTransfer(transfer);
			return ResponseEntity.ok(transferirValidator.createSuccessResponse());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(transferirValidator.createErrorResponse(e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500)
					.body(transferirValidator.createErrorResponse("Erro interno ao agendar a transferencia."));
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Transferir>> getAllTransfers() {
		try {
			List<Transferir> listTransferir = transferRepository.findAll();
			return ResponseEntity.ok(listTransferir);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}
}
