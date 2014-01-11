package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.domain.Card;
import com.app55.domain.User;

public final class CardCreateRequest extends Request<CardCreateResponse>
{
	private User	user;
	private Card	card;
	private String	ipAddress;
	private boolean threeds = false;

	public CardCreateRequest()
	{
		super(CardCreateResponse.class);
	}

	public CardCreateRequest(User user, Card card)
	{
		this(user, card, false);
	}
	public CardCreateRequest(User user, Card card, boolean threeds)
	{
		this();
		this.user = user;
		this.card = card;
		this.setThreeDSecure(threeds);
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/card";
	}

	@Override
	@JsonIgnore
	public String getHttpMethod()
	{
		return "POST";
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

	@JsonProperty(value = "ip_address")
	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
	@JsonProperty(value = "threeds")
	public boolean isThreeDSecure() {
		return threeds;
	}

	public void setThreeDSecure(boolean threeds) {
		this.threeds = threeds;
	}
}