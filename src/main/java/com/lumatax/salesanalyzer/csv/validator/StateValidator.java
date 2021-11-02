package com.lumatax.salesanalyzer.csv.validator;

import com.lumatax.salesanalyzer.model.State;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.validators.StringValidator;
import com.opencsv.exceptions.CsvValidationException;

public class StateValidator implements StringValidator {
	@Override
	public boolean isValid(String s) {
		return State.isAbbreviationValid(s);
	}

	@Override
	public void validate(String s, BeanField beanField) throws CsvValidationException {
		if (!isValid(s)) {
			throw new CsvValidationException(
				String.format("State abbreviation not valid: %s", s));
		}
	}

	@Override
	public void setParameterString(String s) {}
}
