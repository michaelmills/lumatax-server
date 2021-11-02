package com.lumatax.salesanalyzer.csv;

import com.lumatax.salesanalyzer.model.CsvModel;
import com.lumatax.salesanalyzer.model.CsvParseResult;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.List;

@Slf4j
@Service
public class CsvFileParser<T extends CsvModel> {

	/**
	 * Parse csv file, casting rows to specified type T
	 * @param fileReader
	 * @param clazz
	 * @return CsvParseResult - valid and invalid results
	 */
	public CsvParseResult<T> parse(BufferedReader fileReader, Class<T> clazz) {
		CsvToBean<T> beans =
				new CsvToBeanBuilder<T>(fileReader)
						.withType(clazz)
						.withThrowExceptions(false)
						.build();

		List<T> transactions = beans.parse();

		log.debug("Csv file successfully parsed");

		return new CsvParseResult<>(transactions, beans.getCapturedExceptions());
	}
}
