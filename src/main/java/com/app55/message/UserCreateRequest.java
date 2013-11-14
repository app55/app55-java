package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.User;

public final class UserCreateRequest extends Request<UserCreateResponse>
{
	private User	user;

	public UserCreateRequest()
	{
		super(UserCreateResponse.class);
	}

	public UserCreateRequest(User user)
	{
		this();
		this.user = user;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/user";
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