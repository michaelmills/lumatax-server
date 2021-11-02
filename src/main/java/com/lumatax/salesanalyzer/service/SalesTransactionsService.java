package com.lumatax.salesanalyzer.service;

import com.lumatax.salesanalyzer.exception.SalesTransactionsException;
import com.lumatax.salesanalyzer.model.CsvParseResult;
import com.lumatax.salesanalyzer.model.InvalidTransactionsSummary;
import com.lumatax.salesanalyzer.model.SaleTransaction;
import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.model.ValidTransactionsSummary;
import com.lumatax.salesanalyzer.repository.SalesTransactionsRepository;
import com.lumatax.salesanalyzer.csv.CsvFileParser;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SalesTransactionsService {

	@Autowired
	private CsvFileParser<SaleTransaction> csvFileParser;

	@Autowired
	private SalesTransactionsRepository salesTransactionsRepository;

	/**
	 * Parse csv file and process results
	 * @param file - csv file
	 * @return SalesTransactionsSummary - summary of valid and invalid transactions
	 * @throws SalesTransactionsException
	 */
	public SalesTransactionsSummary processFile(MultipartFile file) throws SalesTransactionsException {
		try {
			log.info("Process file: {}", file.getOriginalFilename());

			// parse csv file
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

			CsvParseResult<SaleTransaction> csvParseResult = csvFileParser.parse(fileReader, SaleTransaction.class);
			List<SaleTransaction> validResults = csvParseResult.getValidResults();
			List<CsvException> csvExceptions = csvParseResult.getInvalidExceptions();

			// process csv results
			ValidTransactionsSummary validTransactionsSummary = processValidTransactions(validResults);
			InvalidTransactionsSummary invalidTransactionsSummary = processInvalidTransactions(csvExceptions);

			salesTransactionsRepository.saveAll(validResults);

			SalesTransactionsSummary summary = new SalesTransactionsSummary();
			summary.setValidTransactionsSummary(validTransactionsSummary);
			summary.setInvalidTransactionsSummary(invalidTransactionsSummary);

			log.info("File processed successfully: {}", file.getOriginalFilename());

			return summary;
		} catch (IOException e) {
			throw new SalesTransactionsException(e);
		}
	}

	private ValidTransactionsSummary processValidTransactions(List<SaleTransaction> transactions) {
		ValidTransactionsSummary validTransactionsSummary = new ValidTransactionsSummary();

		transactions.forEach(transaction -> {
			validTransactionsSummary.addDate(transaction.getDate());
			validTransactionsSummary.addSale(transaction.getSale());
			validTransactionsSummary.addTax(transaction.getTax());
		});

		validTransactionsSummary.setNumberOfRows(transactions.size());

		log.debug("Valid sales transactions summary processed");

		return validTransactionsSummary;
	}

	private InvalidTransactionsSummary processInvalidTransactions(List<CsvException> exceptions) {
		InvalidTransactionsSummary invalidTransactionsSummary = new InvalidTransactionsSummary();
		Set<Long> invalidRows = new HashSet<>();

		exceptions.forEach(ex -> {
			log.info("Line {} : {}", ex.getLineNumber(), ex.getMessage());
			invalidRows.add(ex.getLineNumber());
		});

		invalidTransactionsSummary.setNumberOfRows(invalidRows.size());

		log.debug("Invalid sales transactions summary processed");

		return invalidTransactionsSummary;
	}
}
