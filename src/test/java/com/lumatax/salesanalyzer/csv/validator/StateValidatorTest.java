package com.lumatax.salesanalyzer.csv.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateValidatorTest {

	private StateValidator validator;

	@BeforeEach
	void setUp() {
		validator = new StateValidator();
	}

	@Test
	void isValid() {
		assertTrue(validator.isValid("CA"));
		assertTrue(validator.isValid("MA"));
		assertTrue(validator.isValid("FL"));
		assertTrue(validator.isValid("TX"));
	}

	@Test
	void isValid_Invalid() {
		assertFalse(validator.isValid("#%%$$^^%"));
		assertFalse(validator.isValid("123&&&%"));
		assertFalse(validator.isValid("1232_ads"));
		assertFalse(validator.isValid("asd asdfsa asd"));
	}
}