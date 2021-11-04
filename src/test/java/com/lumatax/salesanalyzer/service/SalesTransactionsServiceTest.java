package com.lumatax.salesanalyzer.service;

import com.lumatax.salesanalyzer.exception.SalesTransactionsException;
import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.repository.SalesSummaryRepository;
import com.lumatax.salesanalyzer.repository.SalesTransactionsRepository;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class SalesTransactionsServiceTest {

	@Autowired
	private SalesTransactionsService salesTransactionsService;

	@MockBean
	private SalesTransactionsRepository salesTransactionsRepository;

	@MockBean
	private SalesSummaryRepository salesSummaryRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@BeforeEach
	void setUp() {
	}

	@Test
	public void processFile() throws IOException, SalesTransactionsException {
		String filename = "transactions-1.csv";

		MockMultipartFile file = new MockMultipartFile(filename,
				new FileInputStream(new File("src/test/resources/" + filename)));

		SalesTransactionsSummary summary = salesTransactionsService.processFile(file);

		assertNotNull(summary);
		assertNotNull(summary.getValidTransactionsSummary());
		assertEquals(2, summary.getValidTransactionsSummary().getNumberOfRows());
		assertEquals(new BigDecimal("2000501.94"), summary.getValidTransactionsSummary().getTotalSales());
		assertEquals(new BigDecimal("31.0"), summary.getValidTransactionsSummary().getTotalTax());
		assertEquals(LocalDate.of(2021,1,12), summary.getValidTransactionsSummary().getStartDate());
		assertEquals(LocalDate.of(2021,1,15), summary.getValidTransactionsSummary().getEndDate());

		assertNotNull(summary.getInvalidTransactionsSummary());
		assertEquals(3, summary.getInvalidTransactionsSummary().getNumberOfRows());
	}

	@Test
	public void processFile_InvalidValue() throws IOException, SalesTransactionsException {
		String filename = "transactions-incorrect-value.csv";

		MockMultipartFile file = new MockMultipartFile(filename,
				new FileInputStream(new File("src/test/resources/" + filename)));

		SalesTransactionsSummary summary = salesTransactionsService.processFile(file);

		assertNotNull(summary);
		assertNotNull(summary.getValidTransactionsSummary());
		assertEquals(0, summary.getValidTransactionsSummary().getNumberOfRows());
		assertEquals(new BigDecimal("0"), summary.getValidTransactionsSummary().getTotalSales());
		assertEquals(new BigDecimal("0"), summary.getValidTransactionsSummary().getTotalTax());
		assertNull(summary.getValidTransactionsSummary().getStartDate());
		assertNull(summary.getValidTransactionsSummary().getEndDate());

		assertNotNull(summary.getInvalidTransactionsSummary());
		assertEquals(1, summary.getInvalidTransactionsSummary().getNumberOfRows());
	}

	@Test
	public void processFile_NoHeader() {
		try {
			String filename = "transactions-no-header.csv";

			MockMultipartFile file = new MockMultipartFile(filename, new FileInputStream(new File("src/test/resources/" + filename)));

			SalesTransactionsSummary summary = salesTransactionsService.processFile(file);
		} catch (Exception e) {
			assertTrue(e instanceof SalesTransactionsException);
			assertTrue(e.getMessage().startsWith("Error capturing CSV header!"));
		}
	}
}