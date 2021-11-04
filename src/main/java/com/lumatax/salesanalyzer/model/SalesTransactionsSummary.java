package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "sale_summary")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesTransactionsSummary {

	@Getter(onMethod_ = {@Id, @GeneratedValue(strategy = IDENTITY),
			@Column(name = "id")})
	private int id;

	@Getter(onMethod_ = {@Embedded})
	private ValidTransactionsSummary validTransactionsSummary;

	@Getter(onMethod_ = {@Embedded})
	private InvalidTransactionsSummary invalidTransactionsSummary;
}
