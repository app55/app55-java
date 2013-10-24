package com.app55;

import com.app55.domain.Card;
import com.app55.domain.Transaction;
import com.app55.domain.User;
import com.app55.message.CardCreateRequest;
import com.app55.message.CardDeleteRequest;
import com.app55.message.CardListRequest;
import com.app55.message.TransactionCommitRequest;
import com.app55.message.TransactionCreateRequest;
import com.app55.message.UserAuthenticateRequest;
import com.app55.message.UserCreateRequest;
import com.app55.message.UserUpdateRequest;

public class Gateway
{
	private Environment	environment;
	private String		apiKey;
	private String		apiSecret;

	public Gateway(Environment environment, String apiKey, String apiSecret)
	{
		this.environment = environment;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public Environment getEnvironment()
	{
		return environment;
	}

	public String getApiKey()
	{
		return apiKey;
	}

	public String getApiSecret()
	{
		return apiSecret;
	}

	public CardCreateRequest createCard(User user, Card card)
	{
		CardCreateRequest request = new CardCreateRequest(user, card);
		request.setGateway(this);
		return request;
	}

	public CardDeleteRequest deleteCard(User user, Card card)
	{
		CardDeleteRequest request = new CardDeleteRequest(user, card);
		request.setGateway(this);
		return request;
	}

	public CardListRequest listCards(User user)
	{
		CardListRequest request = new CardListRequest(user);
		request.setGateway(this);
		return request;
	}

	public TransactionCreateRequest createTransaction(User user, Card card, Transaction transaction)
	{
		TransactionCreateRequest request = new TransactionCreateRequest(user, card, transaction);
		request.setGateway(this);
		return request;
	}
	
	public TransactionCreateRequest createTransaction(User user, Card card, Transaction transaction, String ipAddress)
	{
		TransactionCreateRequest request = new TransactionCreateRequest(user, card, transaction, ipAddress);
		request.setGateway(this);
		return request;
	}
	
	public TransactionCreateRequest createTransaction(Card card, Transaction transaction, User user)
	{
		return createTransaction(user, card, transaction);
	}
	
	public TransactionCreateRequest createTransaction(Card card, Transaction transaction)
	{
		TransactionCreateRequest request = new TransactionCreateRequest(card, transaction);
		request.setGateway(this);
		return request;
	}

	public TransactionCommitRequest commitTransaction(Transaction transaction)
	{
		TransactionCommitRequest request = new TransactionCommitRequest(transaction);
		request.setGateway(this);
		return request;
	}

	public UserCreateRequest createUser(User user)
	{
		UserCreateRequest request = new UserCreateRequest(user);
		request.setGateway(this);
		return request;
	}

	public UserAuthenticateRequest authenticateUser(User user)
	{
		UserAuthenticateRequest request = new UserAuthenticateRequest(user);
		request.setGateway(this);
		return request;
	}

	public UserUpdateRequest updateUser(User user)
	{
		UserUpdateRequest request = new UserUpdateRequest(user);
		request.setGateway(this);
		return request;
	}
}