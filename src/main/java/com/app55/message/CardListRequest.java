package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.User;

public final class CardListRequest extends Request<CardListResponse>
{
	private User	user;

	public CardListRequest()
	{
		super(CardListResponse.class);
	}

	public CardListRequest(User user)
	{
		this();
		this.user = user;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/card";
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