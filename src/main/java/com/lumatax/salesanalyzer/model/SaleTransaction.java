package com.lumatax.salesanalyzer.model;

import com.lumatax.salesanalyzer.service.processor.MoneyProcessor;
import com.lumatax.salesanalyzer.service.validator.AlphaNumericValidator;
import com.lumatax.salesanalyzer.service.validator.StateValidator;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import com.opencsv.bean.validators.PreAssignmentValidator;
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
public class SaleTransaction {

	@CsvBindByName(column = "invoice_id")
	@PreAssignmentValidator(validator = AlphaNumericValidator.class)
	private String id;

	@CsvBindByName(column = "invoice_date")
	@CsvDate("M/dd/yy")
	private LocalDate date;

	@CsvBindByName(column = "total_sales")
	@PreAssignmentProcessor(processor = MoneyProcessor.class, paramString = "0")
	private BigDecimal sale;

	@CsvBindByName(column = "total_tax")
	@PreAssignmentProcessor(processor = MoneyProcessor.class, paramString = "0")
	private BigDecimal tax;

	@CsvBindByName(column = "state")
	@PreAssignmentValidator(validator = StateValidator.class)
	private String state;

	@CsvBindByName(column = "ship_to_zip")
	private int zipCode;

}
