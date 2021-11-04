package com.lumatax.salesanalyzer.csv.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AlphaNumericValidatorTest {

	private AlphaNumericValidator validator;

	@BeforeEach
	void setUp() {
		validator = new AlphaNumericValidator();
	}

	@Test
	void isValid() {
		assertTrue(validator.isValid("123"));
		assertTrue(validator.isValid("123assd"));
		assertTrue(validator.isValid("asdfs123"));
		assertTrue(validator.isValid("1sd2ds3"));
		assertTrue(validator.isValid("aadfggfs"));
	}

	@Test
	void isValid_Invalid() {
		assertFalse(validator.isValid("#%%$$^^%"));
		assertFalse(validator.isValid("123&&&%"));
		assertFalse(validator.isValid("1232_ads"));
		assertFalse(validator.isValid("asd asdfsa asd"));
	}
}