package com.app55.test;

import com.app55.Environment;
import com.app55.Gateway;

public class TestConfiguration
{
	private static final String	API_KEY_DEFAULT		= "enter api key here";
	private static final String	API_SECRET_DEFAULT	= "enter api secret here";

	public static final Gateway	GATEWAY				= new Gateway(Environment.SANDBOX, getApiKey(), getApiSecret());

	public static String getApiKey()
	{
		String value = System.getenv("APP55_API_KEY");
		return value != null ? value : API_KEY_DEFAULT;
	}

	public static String getApiSecret()
	{
		String value = System.getenv("APP55_API_SECRET");
		return value != null ? value : API_SECRET_DEFAULT;
	}
}