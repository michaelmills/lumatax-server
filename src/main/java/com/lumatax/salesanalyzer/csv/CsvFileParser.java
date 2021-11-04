package com.lumatax.salesanalyzer.csv;

import com.lumatax.salesanalyzer.model.CsvModel;
import com.lumatax.salesanalyzer.model.CsvParseResult;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class CsvFileParser<T extends CsvModel> {

	/**
	 * Parse csv file, casting rows to specified type T
	 * @param file
	 * @param clazz
	 * @return CsvParseResult - valid and invalid results
	 */
	public CsvParseResult<T> parse(MultipartFile file, Class<T> clazz) throws IOException {
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

		CsvToBean<T> beans = new CsvToBeanBuilder<T>(fileReader).withType(clazz).withThrowExceptions(false).build();

		List<T> transactions = beans.parse();

		log.debug("Csv file successfully parsed");

		return new CsvParseResult<>(transactions, beans.getCapturedExceptions());
	}
}
