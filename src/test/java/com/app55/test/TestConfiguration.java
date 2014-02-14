package com.app55.test;

import com.app55.Environment;
import com.app55.Gateway;

public class TestConfiguration
{
	private static final String	API_KEY_DEFAULT		= "cHvG680shFTaPWhp8RHhGCSo5QbHkWxP";
	private static final String	API_SECRET_DEFAULT	= "zMHzGPF3QAAQQzTDoTGtGz8f5WFZFjzM";
	private static final String	API_ENVIRONMENT_DEFAULT	= "Development";

	public static final Gateway	GATEWAY				= new Gateway(getEnvironment(), getApiKey(), getApiSecret());

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
	public static Environment getEnvironment()
	{
		String value = System.getenv("APP55_API_ENVIRONMENT");
		if (value == null)
			value = API_ENVIRONMENT_DEFAULT;
		
		if (value.equalsIgnoreCase("Development"))
			return Environment.DEVELOPMENT;
		if (value.equalsIgnoreCase("Sandbox"))
			return Environment.SANDBOX;
		if (value.equalsIgnoreCase("Production"))
			return Environment.PRODUCTION;
		
		return Environment.DEVELOPMENT;
	}
}