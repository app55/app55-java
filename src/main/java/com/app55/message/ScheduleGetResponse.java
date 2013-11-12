package com.app55.message;

import com.app55.domain.Card;
import com.app55.domain.Schedule;
import com.app55.domain.Transaction;

public class ScheduleGetResponse extends Response
{
	private Schedule	schedule;
	private Card		card;
	private Transaction	transaction;

	public Schedule getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
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