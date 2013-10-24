package com.app55.error;

import java.util.Map;

public class RequestException extends ApiException
{
	public RequestException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= 4238902349L;
}