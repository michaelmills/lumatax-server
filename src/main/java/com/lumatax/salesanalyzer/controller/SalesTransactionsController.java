package com.lumatax.salesanalyzer.controller;

import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.service.sales.CsvFileParser;
import com.lumatax.salesanalyzer.service.sales.SalesTrasactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("sales/transactions")
public class SalesTransactionsController {
	@Autowired
	private SalesTrasactionsService salesTrasactionsService;

	@Autowired
	private CsvFileParser csvFileParser;

	@PostMapping("file")
	public Mono<SalesTransactionsSummary> uploadFile(@RequestParam("file") MultipartFile file) {

		return Mono.just(csvFileParser.parse(file));

//		var fileParseJob = fileUploader.uploadProcedureFees(file);
//		return Mono.empty();
	}
}
