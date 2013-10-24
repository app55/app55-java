package com.app55.error;

import java.util.Map;

class ValidationException extends ApiException
{
	ValidationException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= -290723122323355L;
}