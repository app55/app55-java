package com.app55.transport;

import java.util.Map;


public interface HttpAdapter
{
	public HttpResponse onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString) throws Exception;

	public HttpResponse onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString, Map<String, String> headers) throws Exception;
	
	public void onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString, HttpListener listener);
}