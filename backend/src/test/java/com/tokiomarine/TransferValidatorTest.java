package com.tokiomarine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tokiomarine.model.Transfer;

public class TransferValidatorTest {

    private TransferValidator transferValidator;

    @BeforeEach
    public void setUp() {
        transferValidator = new TransferValidator();
    }

    @Test
    public void testValidateTransferForScheduling_ValidTransfer() {
        Transfer validTransfer = new Transfer("1234567890", "0987654321", BigDecimal.valueOf(30),
                LocalDate.now().plusDays(5));

        // No exception should be thrown for a valid transfer
        assertDoesNotThrow(() -> transferValidator.validateTransferForScheduling(validTransfer));
    }

    @Test
    public void testValidateTransferForScheduling_InvalidTransferAmount() {
        Transfer invalidTransfer = new Transfer("1234567890", "0987654321", BigDecimal.ZERO,
                LocalDate.now().plusDays(5));

        assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(invalidTransfer));
    }

    @Test
    public void testValidateTransferForScheduling_SameOriginAndDestinationAccount() {
        Transfer invalidTransfer = new Transfer("1234567890", "1234567890", BigDecimal.valueOf(30),
                LocalDate.now().plusDays(5));

        assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(invalidTransfer));
    }

    @Test
    public void testValidateTransferForScheduling_InvalidTransferDate() {
        Transfer invalidTransfer = new Transfer("1234567890", "0987654321", BigDecimal.valueOf(30),
                LocalDate.now().plusDays(60));

        assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(invalidTransfer));
    }

    @Test
    public void testValidateTransferForScheduling_InvalidAccountLength() {
        Transfer invalidTransfer = new Transfer("123456789", "0987654321", BigDecimal.valueOf(30),
                LocalDate.now().plusDays(5));

        assertThrows(IllegalArgumentException.class,
                () -> transferValidator.validateTransferForScheduling(invalidTransfer));
    }

    @Test
    public void testCreateSuccessResponse() {
        assertEquals("Transferência agendada com sucesso!",
                transferValidator.createSuccessResponse().get("message"));
    }

    @Test
    public void testCreateErrorResponse() {
        String errorMessage = "Erro na transferência";
        assertEquals(errorMessage, transferValidator.createErrorResponse(errorMessage).get("error"));
    }
}
