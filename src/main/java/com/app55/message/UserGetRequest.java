package com.app55.message;

import com.app55.domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;

public class UserGetRequest extends Request<UserGetResponse>
{
	private User	user;

	public UserGetRequest()
	{
		super(UserGetResponse.class);
	}

	public UserGetRequest(User user)
	{
		this();
		this.user = user;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/user/" + user.getId();
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