package com.tokiomarine.repository;

import com.tokiomarine.model.Transfer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferirRepository extends JpaRepository<Transfer, Long> {
	@SuppressWarnings("unchecked")
	Transferir save(Transfer transfer) throws RuntimeException;

	List<Transferir> findAll() throws RuntimeException;
}
