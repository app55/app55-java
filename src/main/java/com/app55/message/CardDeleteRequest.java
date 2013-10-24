package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.Card;
import com.app55.domain.User;

public final class CardDeleteRequest extends Request<CardDeleteResponse>
{
	private User	user;
	private Card	card;

	public CardDeleteRequest()
	{
		super(CardDeleteResponse.class);
	}

	public CardDeleteRequest(User user, Card card)
	{
		this();
		this.user = user;
		this.card = card;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/card/" + card.getToken();
	}

	@Override
	@JsonIgnore
	public String getHttpMethod()
	{
		return "DELETE";
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