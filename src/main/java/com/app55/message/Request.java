package com.app55.message;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.error.ApiException;
import com.app55.error.RequestException;
import com.app55.error.InvalidSignatureException;
import com.app55.util.EncodeUtil;
import com.app55.util.JsonUtil;

public abstract class Request<T extends Response> extends Message
{
	private Class<T>	responseClass;

	Request(Class<T> responseClass)
	{
		this.responseClass = responseClass;
	}

	@JsonIgnore
	public abstract String getHttpEndpoint();

	@JsonIgnore
	public String getHttpMethod()
	{
		return "GET";
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "sig")
	public String getSignature()
	{
		return toSignature(true);
	}

	@Override
	@JsonProperty(value = "ts")
	public String getTimestamp()
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	@SuppressWarnings("unchecked")
	public T send() throws ApiException
	{
		try
		{
			URL url;
			HttpURLConnection con = null;

			Map<String, Boolean> exclude = new HashMap<String, Boolean>();
			exclude.put("sig", true);
			exclude.put("ts", true);
			String qs = toFormData(false, exclude);

			if ("GET".equals(getHttpMethod()))
			{
				url = new URL(getHttpEndpoint() + "?" + qs);
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod(getHttpMethod());

				con.setRequestProperty("Authorization", EncodeUtil.createBasicAuthString(getGateway().getApiKey(), getGateway().getApiSecret()));
			}
			else
			{
				byte[] data = qs.getBytes("UTF-8");

				url = new URL(getHttpEndpoint());
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod(getHttpMethod());

				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Authorization", EncodeUtil.createBasicAuthString(getGateway().getApiKey(), getGateway().getApiSecret()));
				con.setFixedLengthStreamingMode(data.length);
				con.setDoOutput(true);

				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.write(data);
				wr.flush();
				wr.close();
			}

			Scanner s;
			if (con.getResponseCode() != 200)
			{
				throw new RequestException("Http Error " + con.getResponseCode(), (long) con.getResponseCode(), null);
			}
			else
			{
				s = new Scanner(con.getInputStream());
			}
			s.useDelimiter("\\Z");
			String json = s.next();

			Map<String, Object> ht = JsonUtil.map(json);
			if (ht.containsKey("error"))
				throw ApiException.createException((Map<String, Object>) ht.get("error"));

			T r = JsonUtil.object(json, responseClass);
			r.populate(ht);
			r.setGateway(getGateway());

			if (!r.isValidSignature())
				throw new InvalidSignatureException();

			return r;
		}
		catch (ApiException a) {
			// This just gets rethrown
			throw a;
		}
		catch (Exception e)
		{
			ApiException.createException(e.getMessage(), -1L);
		}

		return null;
	}
}