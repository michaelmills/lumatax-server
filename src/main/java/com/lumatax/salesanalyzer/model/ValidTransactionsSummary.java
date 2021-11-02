package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidTransactionsSummary {
	private int numberOfRows = 0;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal totalSales = new BigDecimal(0);
	private BigDecimal totalTax = new BigDecimal(0);

	public void addSale(BigDecimal bigDecimal) {
		totalSales = totalSales.add(bigDecimal);
	}

	public void addTax(BigDecimal bigDecimal) {
		totalTax = totalTax.add(bigDecimal);
	}

	public void addDate(LocalDate date) {
		if (startDate == null) {
			startDate = date;
		} else if (startDate.compareTo(date) < 0) {
			startDate = date;
		}

		if (endDate == null) {
			endDate = date;
		} else if (endDate.compareTo(date) > 0) {
			endDate = date;
		}


	}
}
