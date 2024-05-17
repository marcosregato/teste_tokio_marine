package com.tokiomarine;

import com.tokiomarine.model.Transfer;
import com.tokiomarine.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransferRepositoryIntegrationTest {

	@Autowired
	private TransferRepository transferRepository;

	@Test
	public void testSaveAndFindAll() {
		Transfer transfer = new Transfer();

		transferRepository.save(transfer);

		List<Transfer> transfers = transferRepository.findAll();

		assertEquals(1, transfers.size());
		assertEquals(transfer, transfers.get(0));
	}
}
