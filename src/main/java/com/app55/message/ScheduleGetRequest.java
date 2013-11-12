package com.app55.message;

import com.app55.domain.Schedule;
import com.app55.domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;

public class ScheduleGetRequest extends Request<ScheduleGetResponse>
{
	private Schedule	schedule;
	private User		user;

	public ScheduleGetRequest()
	{
		super(ScheduleGetResponse.class);
	}

	public ScheduleGetRequest(User user, Schedule schedule)
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