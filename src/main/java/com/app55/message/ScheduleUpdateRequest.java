package com.app55.message;

import com.app55.domain.Card;
import com.app55.domain.Schedule;
import com.app55.domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;

public class ScheduleUpdateRequest extends Request<ScheduleUpdateResponse>
{
	private Schedule	schedule;
	private User		user;
	private Card		card;

	public ScheduleUpdateRequest()
	{
		super(ScheduleUpdateResponse.class);
	}

	public ScheduleUpdateRequest(User user, Card card, Schedule schedule)
	{
		this();
		this.user = user;
		this.card = card;
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
		return "POST";
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

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}
}