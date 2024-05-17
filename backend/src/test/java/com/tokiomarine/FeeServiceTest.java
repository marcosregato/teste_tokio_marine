package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.math.RoundingMode;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tokiomarine.repository.TransferRepository;
import com.tokiomarine.service.FeeService;

public class FeeServiceTest {

	@BeforeEach
	public void setUp() {
		TransferRepository transferRepository = mock(TransferRepository.class);
		new FeeService(transferRepository);
	}

	@Test
	public void testCalculateTransferRate() {
		FeeService feeService = new FeeService();
		int dias = 50;
		BigDecimal transferAmount = new BigDecimal("100.00");

		BigDecimal result = feeService.calculateTransferRate(dias, transferAmount);

		BigDecimal expectedFee = new BigDecimal("1.70").setScale(2, RoundingMode.HALF_UP);
		BigDecimal marginOfError = new BigDecimal("0.001");

		assertTrue(expectedFee.subtract(result).abs().compareTo(marginOfError) < 0);
		assertEquals(expectedFee, result.setScale(2, RoundingMode.HALF_UP));
	}
}