package com.example.linkgenerator.repo;

import com.example.linkgenerator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>  {
}
