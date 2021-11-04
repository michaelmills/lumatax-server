package com.lumatax.salesanalyzer.controller;

import com.lumatax.salesanalyzer.exception.SalesTransactionsException;
import com.lumatax.salesanalyzer.model.InvalidTransactionsSummary;
import com.lumatax.salesanalyzer.model.SalesTransactionsSummary;
import com.lumatax.salesanalyzer.model.ValidTransactionsSummary;
import com.lumatax.salesanalyzer.service.SalesTransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SalesTransactionsController.class)
class SalesTransactionsControllerTest {

	@MockBean
	private SalesTransactionsService salesTransactionsService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void isParse_Successful() throws Exception {
		String filename = "transactions-1.csv";

		MockMultipartFile file = new MockMultipartFile("file",
				new FileInputStream(new File("src/test/resources/" + filename)));

		SalesTransactionsSummary summary = new SalesTransactionsSummary();
		summary.setValidTransactionsSummary(new ValidTransactionsSummary());
		summary.setInvalidTransactionsSummary(new InvalidTransactionsSummary());

		when(salesTransactionsService.processFile(file)).thenReturn(summary);

		this.mockMvc.perform(multipart("/sales/transactions/file").file(file))
		            .andExpect(status().isOk())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.validTransactionsSummary").exists())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.invalidTransactionsSummary").exists())
		            .andReturn();

	}

	@Test
	public void isParse_Internal_Failure() throws Exception {
		String filename = "transactions-1.csv";

		MockMultipartFile file = new MockMultipartFile("file",
				new FileInputStream(new File("src/test/resources/" + filename)));

		SalesTransactionsSummary summary = new SalesTransactionsSummary();
		summary.setValidTransactionsSummary(new ValidTransactionsSummary());
		summary.setInvalidTransactionsSummary(new InvalidTransactionsSummary());

		when(salesTransactionsService.processFile(file)).thenThrow(new SalesTransactionsException());

		this.mockMvc.perform(multipart("/sales/transactions/file").file(file))
		            .andExpect(status().isInternalServerError())
		            .andReturn();

	}

	@Test
	public void isParse_Invalid_Request() throws Exception {
		String filename = "transactions-1.csv";

		MockMultipartFile file = new MockMultipartFile("incorrect-request-param",
				new FileInputStream(new File("src/test/resources/" + filename)));

		when(salesTransactionsService.processFile(file)).thenThrow(new SalesTransactionsException());

		this.mockMvc.perform(multipart("/sales/transactions/file").file(file))
		            .andExpect(status().isBadRequest())
		            .andReturn();

	}
}