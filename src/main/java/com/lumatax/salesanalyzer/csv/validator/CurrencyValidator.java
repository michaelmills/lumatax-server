package com.lumatax.salesanalyzer.csv.validator;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.validators.StringValidator;
import com.opencsv.exceptions.CsvValidationException;

import java.text.NumberFormat;
import java.text.ParseException;

public class CurrencyValidator implements StringValidator {

	@Override
	public boolean isValid(String s) {
		return true;
	}

	@Override
	public void validate(String s, BeanField beanField) throws CsvValidationException {
		try {
			NumberFormat.getNumberInstance().parse(s);
		} catch (ParseException e) {
			throw new CsvValidationException(String.format("Failed to convert %s to a number", s));
		}
	}

	@Override
	public void setParameterString(String s) {}
}
