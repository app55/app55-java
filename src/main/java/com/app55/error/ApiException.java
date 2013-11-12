package com.app55.error;

import java.util.Map;

public class ApiException extends RuntimeException
{
	private String				message;
	private Long				code;
	private Map<String, Object>	body;

	ApiException(String message, Long code, Map<String, Object> body)
	{
		this.message = message;
		this.code = code;
		this.body = body;
	}

	@Override
	public String toString()
	{
		return message;
	}

	@SuppressWarnings("unchecked")
	public static ApiException createException(Map<String, Object> error)
	{

		String type = (String) error.get("type");
		String message = (String) error.get("message");
		Integer codeInt = ((Integer) error.get("code"));
		Long code = codeInt != null ? codeInt.longValue() : null;
		Map<String, Object> body = (Map<String, Object>) error.get("body");

		if ("request-error".equals(type))
			return new RequestException(message, code, body);
		if ("resource-error".equals(type))
			return new ResourceException(message, code, body);
		if ("authentication-error".equals(type))
			return new AuthenticationException(message, code, body);
		if ("server-error".equals(type))
			return new ServerException(message, code, body);
		if ("validation-error".equals(type))
			return new ValidationException(message, code, body);
		if ("card-error".equals(type))
			return new CardException(message, code, body);

		return new ApiException(message, code, body);
	}

	public static ApiException createException(String message, Long code)
	{
		return new ApiException(message, code, null);
	}

	public String getMessage()
	{
		return message;
	}

	public Long getCode()
	{
		return code;
	}

	public Map<String, Object> getBody()
	{
		return body;
	}

	private static final long	serialVersionUID	= 838615854773225757L;
}