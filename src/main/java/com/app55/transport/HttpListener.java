package com.app55.transport;

public interface HttpListener
{
	public void onResponse(HttpResponse response);

	public void onError(Exception e);
}