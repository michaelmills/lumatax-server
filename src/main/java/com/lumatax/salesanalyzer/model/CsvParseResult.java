package com.lumatax.salesanalyzer.model;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CsvParseResult<T extends CsvModel> {
	private List<T> validResults;
	private List<CsvException> invalidExceptions;
}
