package com.lumatax.salesanalyzer.exception;

public class SalesTransactionsException extends Exception {
	public SalesTransactionsException() {
		this(null, null);
	}

	public SalesTransactionsException(String message) {
		this(message, null);
	}

	public SalesTransactionsException(Throwable cause) {
		this(cause != null ? cause.getMessage() : null, cause);
	}

	public SalesTransactionsException(String message, Throwable cause) {
		super(message);
		if (cause != null) super.initCause(cause);
	}
}
