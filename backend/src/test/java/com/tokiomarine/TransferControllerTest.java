package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tokiomarine.model.Transfer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@SuppressWarnings("null")
	@Test
	public void testScheduleTransfer_ValidTransfer() {
		Transfer validTransfer = new Transfer("1234567890", "0987654321", BigDecimal.valueOf(30),
				LocalDate.now().plusDays(5));

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/transfers/schedule",
				validTransfer, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Transferência agendada com sucesso!"));
	}

	@SuppressWarnings("null")
	@Test
	public void testScheduleTransfer_InvalidTransfer() {
		Transfer invalidTransfer = new Transfer("1234567890", "0987654321", BigDecimal.ZERO,
				LocalDate.now().plusDays(5));

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/transfers/schedule",
				invalidTransfer, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("O valor da transferência deve ser maior que zero."));
	}
}
