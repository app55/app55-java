package com.app55.message;

import com.app55.domain.User;

public final class UserCreateResponse extends Response
{
	private User	user;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}