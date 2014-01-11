package com.app55.message;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.error.ApiException;

public final class ThreeDRedirectRequest extends Request<TransactionCreateResponse> {
	private String redirectUrl;
	
	ThreeDRedirectRequest() {
		super(TransactionCreateResponse.class);
	}
	public ThreeDRedirectRequest(String redirectUrl) {
		this();
		this.redirectUrl = redirectUrl;
	}
	@Override
	public TransactionCreateResponse send() throws ApiException
	{
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		return super.send(headers);
	}
	@JsonIgnore
	@Override
	public String getHttpMethod()
	{
		return "GET";
	}
	@Override
	public String getHttpEndpoint() {
		return this.redirectUrl + "&next=http://dev.app55.com/v1/echo";  //TODO: should be sandbox
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
