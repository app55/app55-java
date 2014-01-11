package com.app55;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.app55.domain.Card;
import com.app55.domain.Schedule;
import com.app55.domain.Transaction;
import com.app55.domain.User;
import com.app55.message.CardCreateRequest;
import com.app55.message.CardDeleteRequest;
import com.app55.message.CardListRequest;
import com.app55.message.ScheduleCreateRequest;
import com.app55.message.ScheduleDeleteRequest;
import com.app55.message.ScheduleGetRequest;
import com.app55.message.ScheduleListRequest;
import com.app55.message.ScheduleUpdateRequest;
import com.app55.message.ThreeDRedirectRequest;
import com.app55.message.TransactionCancelRequest;
import com.app55.message.TransactionCommitRequest;
import com.app55.message.TransactionCreateRequest;
import com.app55.message.UserAuthenticateRequest;
import com.app55.message.UserCreateRequest;
import com.app55.message.UserGetRequest;
import com.app55.message.UserUpdateRequest;
import com.app55.transport.DefaultHttpAdapter;
import com.app55.transport.HttpAdapter;

public class Gateway
{
	private Environment	environment;
	private HttpAdapter	httpAdapter;
	private String		apiKey;
	private String		apiSecret;

	public Gateway(Environment environment, String apiKey, String apiSecret)
	{
		this(environment, apiKey, apiSecret, new DefaultHttpAdapter());
	}

	public Gateway(Environment environment, String apiKey, String apiSecret, HttpAdapter httpAdapter)
	{
		this.environment = environment;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.httpAdapter = httpAdapter;
	}

	public Environment getEnvironment()
	{
		return environment;
	}

	public HttpAdapter getHttpAdapter()
	{
		return httpAdapter;
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
	
	public CardCreateRequest createCard(User user, Card card, boolean threeds)
	{
		CardCreateRequest request = new CardCreateRequest(user, card, threeds);
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
	public TransactionCreateRequest createTransaction(User user, Card card, Transaction transaction, boolean threeds)
	{
		TransactionCreateRequest request = new TransactionCreateRequest(user, card, transaction, threeds);
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

	public TransactionCancelRequest cancelTransaction(User user, Transaction transaction)
	{
		TransactionCancelRequest request = new TransactionCancelRequest(user, transaction);
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

	public UserGetRequest getUser(User user)
	{
		UserGetRequest request = new UserGetRequest(user);
		request.setGateway(this);
		return request;
	}

	public ScheduleCreateRequest createSchedule(User user, Card card, Transaction transaction, Schedule schedule)
	{
		ScheduleCreateRequest request = new ScheduleCreateRequest(user, card, transaction, schedule);
		request.setGateway(this);
		return request;
	}

	public ScheduleGetRequest getSchedule(User user, Schedule schedule)
	{
		ScheduleGetRequest request = new ScheduleGetRequest(user, schedule);
		request.setGateway(this);
		return request;
	}

	public ScheduleUpdateRequest updateSchedule(User user, Card card, Schedule schedule)
	{
		ScheduleUpdateRequest request = new ScheduleUpdateRequest(user, card, schedule);
		request.setGateway(this);
		return request;
	}

	public ScheduleListRequest listSchedule(User user, Boolean active)
	{
		ScheduleListRequest request = new ScheduleListRequest(user);
		request.setActive(active);
		request.setGateway(this);
		return request;
	}

	public ScheduleDeleteRequest deleteSchedule(User user, Schedule schedule)
	{
		ScheduleDeleteRequest request = new ScheduleDeleteRequest(user, schedule);
		request.setGateway(this);
		return request;
	}
}