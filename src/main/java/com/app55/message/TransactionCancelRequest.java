package com.app55.message;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.app55.domain.Transaction;
import com.app55.domain.User;

public final class TransactionCancelRequest extends Request<TransactionCancelResponse>
{
	private User		user;
	private Transaction	transaction;

	public TransactionCancelRequest()
	{
		super(TransactionCancelResponse.class);
	}

	public TransactionCancelRequest(User user, Transaction transaction)
	{
		this();
		this.user = user;
		this.transaction = transaction;
	}

	@Override
	@JsonIgnore
	public String getHttpEndpoint()
	{
		return getGateway().getEnvironment().getBaseUrl() + "/transaction/" + transaction.getId();
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

	public Transaction getTransaction()
	{
		return transaction;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
}