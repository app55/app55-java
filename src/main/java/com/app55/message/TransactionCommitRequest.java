package com.app55.message;

import com.app55.domain.Transaction;
import org.codehaus.jackson.annotate.JsonIgnore;

public final class TransactionCommitRequest extends Request<TransactionCommitResponse>
{
	private Transaction	transaction;

	public TransactionCommitRequest()
	{
		super(TransactionCommitResponse.class);
	}

	public TransactionCommitRequest(Transaction transaction)
	{
		this();
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
		return "POST";
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