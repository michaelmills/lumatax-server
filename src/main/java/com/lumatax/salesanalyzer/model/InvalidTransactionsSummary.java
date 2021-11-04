package com.lumatax.salesanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvalidTransactionsSummary {

	@Getter(onMethod_ = {@Column(name = "invalid_rows")})
	private int numberOfRows = 0;
}
