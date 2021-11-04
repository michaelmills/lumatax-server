package com.lumatax.salesanalyzer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
class ValidTransactionsSummaryTest {

	private ValidTransactionsSummary summary;

	@BeforeEach
	void setUp() {
		summary = new ValidTransactionsSummary();
	}

	@Test
	void addSale() {
		assertEquals(0, summary.getTotalSales().intValue());

		summary.addSale(new BigDecimal(100));
		summary.addSale(new BigDecimal(500));
		summary.addTax(null);

		assertEquals(600, summary.getTotalSales().intValue());
	}

	@Test
	void addTax() {
		assertEquals(0, summary.getTotalTax().intValue());

		summary.addTax(new BigDecimal(200));
		summary.addTax(new BigDecimal(500));
		summary.addTax(null);

		assertEquals(700, summary.getTotalTax().intValue());
	}

	@Test
	void addDate() {
		assertNull(summary.getStartDate());
		assertNull(summary.getEndDate());

		LocalDate end = LocalDate.of(2020, 10, 20);
		LocalDate middle = LocalDate.of(1990, 1, 2);
		LocalDate start = LocalDate.of(1700, 5, 11);

		summary.addDate(end);
		summary.addDate(start);
		summary.addDate(middle);

		assertEquals(start, summary.getStartDate());
		assertEquals(end, summary.getEndDate());
	}

	@Test
	void addDate_SameDates() {
		assertNull(summary.getStartDate());
		assertNull(summary.getEndDate());

		LocalDate date = LocalDate.of(2020, 10, 20);
		LocalDate date1 = LocalDate.of(2020, 10, 20);
		LocalDate date2 = LocalDate.of(2020, 10, 20);

		summary.addDate(date);
		summary.addDate(date2);
		summary.addDate(date1);
		summary.addDate(null);

		assertEquals(date, summary.getStartDate());
		assertEquals(date, summary.getEndDate());
	}
}