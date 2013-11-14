package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.domain.Card;
import com.app55.domain.Transaction;
import com.app55.domain.User;

public final class TransactionCreateRequest extends Request<TransactionCreateResponse>
{
	private User		user;
	private Card		card;
	private Transaction	transaction;
	private String		ipAddress;

	public TransactionCreateRequest()
	{
		super(TransactionCreateResponse.class);
	}

	public TransactionCreateRequest(User user, Card card, Transaction transaction)
	{
		this();
		this.user = user;
		this.card = card;
		this.transaction = transaction;
	}

	public TransactionCreateRequest(User user, Card card, Transaction transaction, String ipAddress)
	{
		this(user, card, transaction);
		this.ipAddress = ipAddress;
	}

	public TransactionCreateRequest(Card card, Transaction transaction)
	{
		this(null, card, transaction);
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/transaction";
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

	public Transaction getTransaction()
	{
		return transaction;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
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
}