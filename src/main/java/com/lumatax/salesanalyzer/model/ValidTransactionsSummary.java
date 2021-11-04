package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidTransactionsSummary {

	@Getter(onMethod_ = {@Column(name = "valid_rows")})
	private int numberOfRows = 0;

	@Getter(onMethod_ = {@Column(name = "start_date")})
	private LocalDate startDate;

	@Getter(onMethod_ = {@Column(name = "end_date")})
	private LocalDate endDate;

	@Getter(onMethod_ = {@Column(name = "total_sale")})
	private BigDecimal totalSales = new BigDecimal(0);

	@Getter(onMethod_ = {@Column(name = "total_tax")})
	private BigDecimal totalTax = new BigDecimal(0);

	public void addSale(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			totalSales = totalSales.add(bigDecimal);
		}
	}

	public void addTax(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			totalTax = totalTax.add(bigDecimal);
		}
	}

	public void addDate(LocalDate date) {
		if (date == null) {
			return;
		}

		if (startDate == null) {
			startDate = date;
		} else if (startDate.compareTo(date) > 0) {
			startDate = date;
		}

		if (endDate == null) {
			endDate = date;
		} else if (endDate.compareTo(date) < 0) {
			endDate = date;
		}


	}
}
