package com.lumatax.salesanalyzer.csv;

import com.lumatax.salesanalyzer.model.CsvParseResult;
import com.lumatax.salesanalyzer.model.SaleTransaction;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
class CsvFileParserTest {

	private CsvFileParser<SaleTransaction> csvParser;

	@BeforeEach
	void setUp() {
		csvParser = new CsvFileParser<>();
	}

	@Test
	void isParse_Valid() throws IOException {
		String filename = "transactions-1.csv";

		MockMultipartFile file = new MockMultipartFile(filename,
				new FileInputStream(new File("src/test/resources/" + filename)));

		CsvParseResult result = csvParser.parse(file, SaleTransaction.class);

		assertNotNull(result);
		assertNotNull(result.getValidResults());
		assertNotNull(result.getInvalidExceptions());
	}

	@Test
	void isParse_No_Header() {
		try {
			String filename = "transactions-no-header.csv";

			MockMultipartFile file = new MockMultipartFile(filename,
					new FileInputStream(new File("src/test/resources/" + filename)));

			CsvParseResult result = csvParser.parse(file, SaleTransaction.class);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof CsvRequiredFieldEmptyException);
			assertTrue(e.getCause().getMessage().startsWith("Header is missing required fields"));
		}
	}

	@Test
	void isParse_Missing_Header_Column() {
		try {
			String filename = "transactions-missing-header-column.csv";

			MockMultipartFile file = new MockMultipartFile(filename,
					new FileInputStream(new File("src/test/resources/" + filename)));

			CsvParseResult result = csvParser.parse(file, SaleTransaction.class);
		} catch (Exception e) {
			assertTrue(e.getCause() instanceof CsvRequiredFieldEmptyException);
			assertTrue(e.getCause().getMessage().startsWith("Header is missing required fields"));
		}
	}

	@Test
	void isParse_Incorrect_Value() throws IOException {
		String filename = "transactions-incorrect-value.csv";

		MockMultipartFile file = new MockMultipartFile(filename,
				new FileInputStream(new File("src/test/resources/" + filename)));

		CsvParseResult result = csvParser.parse(file, SaleTransaction.class);

		assertFalse(result.getInvalidExceptions().isEmpty());
		assertTrue(result.getInvalidExceptions().get(0) instanceof CsvRequiredFieldEmptyException);
	}

	@Test
	void isParse_Multiple_Errors() throws IOException {
		String filename = "transactions-multiple-errors.csv";

		MockMultipartFile file = new MockMultipartFile(filename,
				new FileInputStream(new File("src/test/resources/" + filename)));

		CsvParseResult result = csvParser.parse(file, SaleTransaction.class);

		assertFalse(result.getInvalidExceptions().isEmpty());
		assertEquals(8, result.getInvalidExceptions().size());
	}
}