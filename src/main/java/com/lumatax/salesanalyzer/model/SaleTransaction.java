package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumatax.salesanalyzer.csv.processor.MoneyProcessor;
import com.lumatax.salesanalyzer.csv.validator.AlphaNumericValidator;
import com.lumatax.salesanalyzer.csv.validator.StateValidator;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import com.opencsv.bean.validators.MustMatchRegexExpression;
import com.opencsv.bean.validators.PreAssignmentValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sale_transaction")
public class SaleTransaction implements CsvModel {

	@Getter(onMethod_ = {@Id, @GeneratedValue(strategy = IDENTITY),
			@Column(name = "id")})
	private int id;

	@Getter(onMethod_ = {@Column(name = "invoice_id")})
	@CsvBindByName(column = "invoice_id", required = true)
	@PreAssignmentValidator(validator = AlphaNumericValidator.class)
	private String invoiceId;

	@Getter(onMethod_ = {@Column(name = "date")})
	@CsvBindByName(column = "invoice_date", required = true)
	@CsvDate("M/d/yy")
	private LocalDate date;

	@Getter(onMethod_ = {@Column(name = "sale")})
	@CsvBindByName(column = "total_sales", required = true)
	@PreAssignmentProcessor(processor = MoneyProcessor.class)
	private BigDecimal sale;

	@Getter(onMethod_ = {@Column(name = "tax")})
	@CsvBindByName(column = "total_tax", required = true)
	@PreAssignmentProcessor(processor = MoneyProcessor.class)
	private BigDecimal tax;

	@Getter(onMethod_ = {@Column(name = "state")})
	@CsvBindByName(column = "state", required = true)
	@PreAssignmentValidator(validator = StateValidator.class)
	private String state;

	@Getter(onMethod_ = {@Column(name = "zipcode")})
	@CsvBindByName(column = "ship_to_zip", required = true)
	@PreAssignmentValidator(validator = MustMatchRegexExpression.class, paramString = "^[0-9]{5}$")
	private String zipcode;

}
