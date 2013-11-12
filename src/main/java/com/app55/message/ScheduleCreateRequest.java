package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.Card;
import com.app55.domain.Schedule;
import com.app55.domain.Transaction;
import com.app55.domain.User;

public class ScheduleCreateRequest extends Request<ScheduleCreateResponse>
{
	private Schedule	schedule;
	private User		user;
	private Card		card;
	private Transaction	transaction;

	public ScheduleCreateRequest()
	{
		super(ScheduleCreateResponse.class);
	}

	public ScheduleCreateRequest(User user, Card card, Transaction transaction, Schedule schedule)
	{
		this();
		this.user = user;
		this.card = card;
		this.transaction = transaction;
		this.schedule = schedule;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/schedule";
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

	public Transaction getTransaction()
	{
		return transaction;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
}