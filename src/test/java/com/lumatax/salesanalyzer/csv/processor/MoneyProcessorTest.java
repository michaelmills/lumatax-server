package com.lumatax.salesanalyzer.csv.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class MoneyProcessorTest {

	private MoneyProcessor processor;

	@BeforeEach
	void setUp() {
		processor = new MoneyProcessor();
	}

	@Test
	void isProcessString() {
		assertEquals("2000", processor.processString("2,000"));
		assertEquals("512000", processor.processString("512,000"));
		assertEquals("512000123", processor.processString("512,000,123"));
	}

}