package com.lumatax.salesanalyzer.csv.processor;

import com.opencsv.bean.processor.StringProcessor;

public class MoneyProcessor implements StringProcessor {

	@Override
	public String processString(String s) {
		return s.replaceAll(",", "");
	}

	@Override
	public void setParameterString(String s) {}
}
