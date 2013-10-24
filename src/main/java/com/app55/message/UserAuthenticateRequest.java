package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.User;

public final class UserAuthenticateRequest extends Request<UserAuthenticateResponse>
{
	private User	user;

	public UserAuthenticateRequest()
	{
		super(UserAuthenticateResponse.class);
	}

	public UserAuthenticateRequest(User user)
	{
		this();
		this.user = user;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/user/authenticate";
	}

	@Override
	@JsonIgnore
	public String getHttpMethod()
	{
		return "POST";
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}