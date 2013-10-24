package com.app55.error;

import java.util.Map;

class ResourceException extends ApiException
{
	ResourceException(String message, Long code, Map<String, Object> body)
	{
		super(message, code, body);
	}

	private static final long	serialVersionUID	= -231333L;
}