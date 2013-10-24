package com.app55.error;

import java.util.Map;

class CardException extends ApiException
{
	CardException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= 2313111L;
}