package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidTransactionsSummary {
	private int numberOfRows;
	private Date startDate;
	private Date endDate;
	private BigDecimal totalSales = new BigDecimal(0);
	private BigDecimal totalTax = new BigDecimal(0);

	public void addSale(BigDecimal bigDecimal) {
		totalSales = totalSales.add(bigDecimal);
	}

	public void addTax(BigDecimal bigDecimal) {
		totalTax = totalTax.add(bigDecimal);
	}
}
