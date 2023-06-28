package br.com.dimdim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dimdim.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	public List<Transaction> findByUser_Id(Long id);
}
