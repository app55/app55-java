package com.app55.error;

import java.util.Map;

class ServerException extends ApiException
{
	ServerException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= 234789234789L;
}