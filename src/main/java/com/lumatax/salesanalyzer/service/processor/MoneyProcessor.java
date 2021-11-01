package com.lumatax.salesanalyzer.service.processor;

import com.opencsv.bean.processor.StringProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;

@Slf4j
public class MoneyProcessor implements StringProcessor {
	String defaultValue;

	@Override
	public String processString(String s) {
//		try {
//			return StringUtils.isBlank(s) ? defaultValue : String.valueOf(NumberFormat.getNumberInstance().parse(s).floatValue());
			return StringUtils.isBlank(s) ? defaultValue : s.replaceAll(",", "");
//		} catch (ParseException e) {
//			log.error(String.format("Failed to process value \"%s\" as a number", s), e);
//			return null;
//		}
	}

	@Override
	public void setParameterString(String s) {
		defaultValue = s;
	}
}
