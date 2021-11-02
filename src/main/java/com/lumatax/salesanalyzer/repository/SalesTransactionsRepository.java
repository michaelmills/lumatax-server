package com.lumatax.salesanalyzer.repository;

import com.lumatax.salesanalyzer.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesTransactionsRepository extends JpaRepository<SaleTransaction, Integer> {}
