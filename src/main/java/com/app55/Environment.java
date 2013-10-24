package com.app55;

public class Environment
{
	private String	server;
	private int		port;
	private boolean	ssl;
	private int		version;

	public Environment(String server, int port, boolean ssl, int version)
	{
		this.server = server;
		this.port = port;
		this.ssl = ssl;
		this.version = version;
	}

	public String getBaseUrl()
	{
		return getScheme() + "://" + getHost() + "/v" + version;
	}

	private String getScheme()
	{
		return ssl ? "https" : "http";
	}

	private String getHost()
	{
		if (port == 443 && ssl)
			return server;
		else if (port == 80 && !ssl)
			return server;
		else
			return server + ":" + port;
	}

	public static final Environment	DEVELOPMENT	= new Environment("dev.app55.com", 80, false, 1);
	public static final Environment	SANDBOX		= new Environment("sandbox.app55.com", 443, true, 1);
	public static final Environment	PRODUCTION	= new Environment("api.app55.com", 443, true, 1);
}