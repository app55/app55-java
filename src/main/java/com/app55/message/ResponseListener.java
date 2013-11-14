package com.app55.message;

import com.app55.error.ApiException;

public interface ResponseListener<R extends Response>
{
	public void onResponse(R response);

	public void onError(ApiException e);
}