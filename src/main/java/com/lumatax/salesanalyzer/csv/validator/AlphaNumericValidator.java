package com.lumatax.salesanalyzer.csv.validator;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.validators.StringValidator;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.StringUtils;

public class AlphaNumericValidator implements StringValidator {

	@Override
	public boolean isValid(String s) {
		return StringUtils.isAlphanumeric(s);
	}

	@Override
	public void validate(String s, BeanField beanField) throws CsvValidationException {
		if (!this.isValid(s)) {
			throw new CsvValidationException(
				String.format("Field %s value \"%s\" is not alphanumeric", beanField.getField().getName(), s));
		}
	}

	@Override
	public void setParameterString(String s) {}
}
