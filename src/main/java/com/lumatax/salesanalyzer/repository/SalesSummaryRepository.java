package com.lumatax.salesanalyzer.repository;

import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesSummaryRepository extends JpaRepository<SalesTransactionsSummary, Integer> {}
