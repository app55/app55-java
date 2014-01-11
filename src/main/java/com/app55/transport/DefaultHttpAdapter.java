package com.app55.transport;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class DefaultHttpAdapter implements HttpAdapter
{
	private ExecutorService	threadPool;

	public DefaultHttpAdapter()
	{
		this(3);
	}

	public DefaultHttpAdapter(int threads)
	{
		threadPool = Executors.newFixedThreadPool(threads);
	}

	public DefaultHttpAdapter(ExecutorService threadPool)
	{
		this.threadPool = threadPool;
	}

	@Override
	public HttpResponse onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString) throws Exception
	{
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod(httpMethod);

		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Authorization", basicAuthString);
		
		if (data != null)
		{
			con.setFixedLengthStreamingMode(data.length);
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
		}
		Scanner s = null;
		if (con.getResponseCode() > 400) {
			InputStream is = con.getErrorStream();
			if (is != null) {
				s = new Scanner(is);
			}
		}
		else {
			s = new Scanner(con.getInputStream());
		}
		String content = "";
		if (s != null) {
			s.useDelimiter("\\Z");
			content = s.next();
		}

		return new HttpResponse(con.getResponseCode(), content);
	}
	
	public HttpResponse onSendRequest(byte[] data, String url, String httpMethod, String basicAuthString, Map<String, String> headers) throws Exception
	{
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod(httpMethod);

		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			con.setRequestProperty(entry.getKey(), entry.getValue());
		}
		con.setRequestProperty("Authorization", basicAuthString);
		

		if (data != null)
		{
			con.setFixedLengthStreamingMode(data.length);
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
		}

		Scanner s = new Scanner(con.getInputStream());
		s.useDelimiter("\\Z");
		String content = s.next();

		return new HttpResponse(con.getResponseCode(), content);
	}

	@Override
	public void onSendRequest(final byte[] data, final String url, final String httpMethod, final String basicAuthString, final HttpListener listener)
	{
		FutureTask<HttpResponse> task = new FutureTask<HttpResponse>(new Callable<HttpResponse>() {

			@Override
			public HttpResponse call() throws Exception
			{
				try
				{
					HttpResponse response = onSendRequest(data, url, httpMethod, basicAuthString);
					listener.onResponse(response);
					return response;
				}
				catch (Exception e)
				{
					listener.onError(e);
					return null;
				}
			}
		});

		threadPool.execute(task);
	}
}