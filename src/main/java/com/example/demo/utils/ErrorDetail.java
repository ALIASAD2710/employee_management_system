package com.example.demo.utils;

public class ErrorDetail {
	private String field;
	private String errorMessage;

	public ErrorDetail(String field, String errorMessage) {
		this.field = field;
		this.errorMessage = errorMessage;
	}

	public String getField() {
		return field;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
