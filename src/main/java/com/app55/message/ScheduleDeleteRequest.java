package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.Schedule;
import com.app55.domain.User;

public class ScheduleDeleteRequest extends Request<ScheduleDeleteResponse>
{
	private Schedule	schedule;
	private User		user;

	public ScheduleDeleteRequest()
	{
		super(ScheduleDeleteResponse.class);
	}

	public ScheduleDeleteRequest(User user, Schedule schedule)
	{
		this();
		this.user = user;
		this.schedule = schedule;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/schedule/" + schedule.getId();
	}

	@Override
	@JsonIgnore
	public String getHttpMethod()
	{
		return "DELETE";
	}

	public Schedule getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
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