package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.User;

public class ScheduleListRequest extends Request<ScheduleListResponse>
{
	private User	user;
	private Boolean	active;

	public ScheduleListRequest()
	{
		super(ScheduleListResponse.class);
	}

	public ScheduleListRequest(User user)
	{
		this();
		this.user = user;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/schedule";
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}
}