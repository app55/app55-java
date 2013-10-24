package com.app55.error;

public class InvalidSignatureException extends ApiException
{
	public InvalidSignatureException()
	{
		super("The response contained an invalid signature.", null, null);
	}

	private static final long	serialVersionUID	= -6591402342429266192L;
}