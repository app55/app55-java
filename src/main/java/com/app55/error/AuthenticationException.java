package com.app55.error;

import java.util.Map;

class AuthenticationException extends ApiException
{
	AuthenticationException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= -2318907231L;
}