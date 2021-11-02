package com.lumatax.salesanalyzer.controller;

import com.lumatax.salesanalyzer.exception.SalesTransactionsException;
import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.service.SalesTransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("sales/transactions")
public class SalesTransactionsController {
	@Autowired
	private SalesTransactionsService salesTransactionsService;

	@PostMapping("file")
	public ResponseEntity<SalesTransactionsSummary> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			return ResponseEntity.ok(salesTransactionsService.processFile(file));
		} catch (SalesTransactionsException e) {
			log.error("Failed to process file {} : {}", file.getOriginalFilename(), e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
