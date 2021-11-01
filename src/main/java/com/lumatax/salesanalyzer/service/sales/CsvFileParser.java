package com.lumatax.salesanalyzer.service.sales;


import com.lumatax.salesanalyzer.model.InvalidTransactionsSummary;
import com.lumatax.salesanalyzer.model.SaleTransaction;
import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.model.ValidTransactionsSummary;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CsvFileParser {

	public SalesTransactionsSummary parse(MultipartFile file) {
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

			CsvToBean<SaleTransaction> beans = new CsvToBeanBuilder<SaleTransaction>(fileReader).withType(
					SaleTransaction.class).withThrowExceptions(false).build();

			List<SaleTransaction> transactions = beans.parse();

			log.info("Sales transactions parsed from file: {}", file.getOriginalFilename());

			Date startDate = null;
			Date endDate = null;

			ValidTransactionsSummary validTransactionsSummary = new ValidTransactionsSummary();

			transactions.forEach(transaction -> {
				validTransactionsSummary.addSale(transaction.getSale());
				validTransactionsSummary.addTax(transaction.getTax());

//				if (startDate == null) {
//					startDate = transaction.getDate();
//				}
			});

			validTransactionsSummary.setNumberOfRows(transactions.size());

			// TODO incorrect way of determining invalid rows
			InvalidTransactionsSummary invalidTransactionsSummary = new InvalidTransactionsSummary();
			invalidTransactionsSummary.setNumberOfRows(beans.getCapturedExceptions().size());

			SalesTransactionsSummary summary = new SalesTransactionsSummary();
			summary.setValidTransactionsSummary(validTransactionsSummary);
			summary.setInvalidTransactionsSummary(invalidTransactionsSummary);

			beans.getCapturedExceptions().forEach(ex -> {
				log.error("", ex);
			});

			return summary;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
