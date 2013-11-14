package com.app55.transport;


public interface HttpAdapter
{
	public HttpResponse onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString) throws Exception;

	public void onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString, HttpListener listener);
}