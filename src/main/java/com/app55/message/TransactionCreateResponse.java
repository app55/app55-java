package com.app55.message;

import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.domain.Transaction;

public final class TransactionCreateResponse extends Response
{
	private Transaction	transaction;
	private String threeDSecureRedirectUrl;

	public Transaction getTransaction()
	{
		return transaction;
	}

	public void setTransaction(Transaction transaction)
	{
		this.transaction = transaction;
	}
	@JsonProperty(value = "threeds")
	public String getThreeDSecureRedirectUrl() {
		return threeDSecureRedirectUrl;
	}

	public void setThreeDSecureRedirectUrl(String threeDSecureRedirectUrl) {
		this.threeDSecureRedirectUrl = threeDSecureRedirectUrl;
	}

}