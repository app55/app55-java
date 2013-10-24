package com.app55.message;

import com.app55.domain.Transaction;

public final class TransactionCreateResponse extends Response
{
	private Transaction	transaction;

	public Transaction getTransaction()
	{
		return transaction;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
}